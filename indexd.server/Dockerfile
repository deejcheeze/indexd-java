FROM jboss/wildfly

ARG WAR
ADD target/${WAR} /opt/jboss/wildfly/standalone/deployments/indexd.war

# setup postgresql
ADD .docker/postgresql /opt/jboss/wildfly/modules/system/layers/base/org/postgresql/

# customizations
ADD .docker/standalone.xml /opt/jboss/wildfly/standalone/configuration/
RUN /opt/jboss/wildfly/bin/add-user.sh kulgan openMinds --silent

ENTRYPOINT ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0", "-Dee8.preview.mode=true"]