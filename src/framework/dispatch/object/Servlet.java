package framework.dispatch.object;

import framework.dispatch.object.Parameter;

public class Servlet {
        private String servletName;
        private String sdboType;
        private String namespace;
        private String permission;
        private Parameter parameterList[];

        public Servlet(String servletName, String sdboType, String namespace, String permission, Parameter[] parameterList) {
                super();
                this.servletName = servletName;
                this.sdboType = sdboType;
                this.namespace = namespace;
                this.permission = permission;
                this.parameterList = parameterList;
        }

        public String getServletName() {
                return servletName;
        }

        public String getSdboType() {
                return sdboType;
        }

        public String getNamespace() {
                return namespace;
        }

        public String getPermission() {
                return permission;
        }

        public Parameter[] getParameterList() {
                return parameterList;
        }
}