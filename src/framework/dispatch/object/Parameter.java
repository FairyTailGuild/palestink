package framework.dispatch.object;

public class Parameter {
        private String name;
        private String type;
        private String format;
        private String transform;
        private Object constant;
        private boolean isNull;
        private String fileType;
        private long fileMaxSize;

        public Parameter(String name, String type, String format, String transform, Object constant, boolean isNull, String fileType, long fileMaxSize) {
                super();
                this.name = name;
                this.type = type;
                this.format = format;
                this.transform = transform;
                this.constant = constant;
                this.isNull = isNull;
                this.fileType = fileType;
                this.fileMaxSize = fileMaxSize;
        }

        public String getName() {
                return name;
        }

        public String getType() {
                return type;
        }

        public String getFormat() {
                return format;
        }

        public String getTransform() {
                return transform;
        }

        public Object getConstant() {
                return constant;
        }

        public boolean isNull() {
                return isNull;
        }

        public String getFileType() {
                return fileType;
        }

        public long getFileMaxSize() {
                return fileMaxSize;
        }
}