import com.google.protobuf.DescriptorProtos;
import com.google.protobuf.Descriptors;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.protobuf.DescriptorProtos.FileDescriptorProto;
import com.google.protobuf.DescriptorProtos.FileDescriptorSet;
import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Descriptors.DescriptorValidationException;
import com.google.protobuf.Descriptors.FileDescriptor;
import com.google.protobuf.Descriptors.GenericDescriptor;

/**
 * @author wujingyi
 */
public class ProtobufDescriptorPool {

    public static ProtobufDescriptorPool instance = new ProtobufDescriptorPool();

    private final Map<String, FileDescriptorProto> fileDescriptorProtoMap = new HashMap<>();
    private final Map<String, FileDescriptor> fileDescriptorMap = new HashMap<>();
    private final Map<String, GenericDescriptor> genericDescriptorMap = new HashMap<>();

    private ProtobufDescriptorPool() {
    }

    /**
     * @param fileNames
     *            可以是路径，也可以是具体文件，文件以.ds为后缀
     */
    public synchronized void loadFiles(String... fileNames) {
        if (fileNames == null) {
            return;
        }
        for (String fileName : fileNames) {
            initFileDescriptorProtoMap(fileName);
            initFileDescriptorMap();
        }
    }

    private void initFileDescriptorProtoMap(String fileName) {
        Utils.recusiveFile(new File(fileName), file -> {
            if (!file.getName().endsWith(".ds")) {
                return;
            }
            try {
                FileDescriptorSet fds = FileDescriptorSet.parseFrom(new FileInputStream(file));
                fds.getFileList().forEach(fdp -> {
                    fileDescriptorProtoMap.put(fdp.getName(), fdp);
                });
            } catch (Throwable t) {
//                PBWLogger.defaultLog.error("<ProtobufDescriptorPool> parse file({}) error. ", file.getName(), t);
            }
        });
    }

    private void initFileDescriptorMap() {
        for (String fileName : fileDescriptorProtoMap.keySet().toArray(new String[0])) {
            try {
                loadFileDescriptor(fileName);
            } catch (DescriptorValidationException e) {
//                PBWLogger.defaultLog.error("<ProtobufDescriptorPool> load file descriptor({}) error. ", fileName, e);
            }
        }
    }

    private void loadFileDescriptor(String fileName) throws DescriptorValidationException {
        FileDescriptorProto fdp = fileDescriptorProtoMap.get(fileName);
        if (fdp == null) {
            return;
        }
        List<FileDescriptor> dependencyList = new ArrayList<>(fdp.getDependencyCount());
        for (String dependencyFileName : fdp.getDependencyList()) {
            loadFileDescriptor(dependencyFileName);
            FileDescriptor dependency = fileDescriptorMap.get(dependencyFileName);
            if (dependency != null) {
                dependencyList.add(dependency);
            }
        }
        FileDescriptor fd = FileDescriptor.buildFrom(fdp, dependencyList.toArray(new FileDescriptor[0]));
        recusiveFileDescriptor(fd);
        fileDescriptorMap.put(fd.getFullName(), fd);
        fileDescriptorProtoMap.remove(fileName);
    }

    private void recusiveFileDescriptor(FileDescriptor fd) {
        fd.getEnumTypes().forEach(descriptor -> genericDescriptorMap.put(descriptor.getFullName(), descriptor));
        fd.getExtensions().forEach(descriptor -> genericDescriptorMap.put(descriptor.getFullName(), descriptor));
        fd.getMessageTypes().forEach(this::recusiveDescriptor);
        fd.getServices().forEach(descriptor -> genericDescriptorMap.put(descriptor.getFullName(), descriptor));
    }

    private void recusiveDescriptor(Descriptor descriptor) {
        genericDescriptorMap.put(descriptor.getFullName(), descriptor);
        descriptor.getEnumTypes().forEach(nestedDescriptor -> genericDescriptorMap.put(nestedDescriptor.getFullName(), nestedDescriptor));
        descriptor.getExtensions().forEach(nestedDescriptor -> genericDescriptorMap.put(nestedDescriptor.getFullName(), nestedDescriptor));
        descriptor.getNestedTypes().forEach(this::recusiveDescriptor);
    }

    public Descriptor getDescriptor(String name) {
        GenericDescriptor descriptor = genericDescriptorMap.get(name);
        if (!(descriptor instanceof Descriptor)) {
            return null;
        }
        return (Descriptor) descriptor;
    }

}