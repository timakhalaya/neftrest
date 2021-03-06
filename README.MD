## Camel + Spring + SOAP Transformation using a REST Facade
<br>
The project uses maven as a tool for building our project.
<br>
 - <b>mvn install</b> for installing all dependencies<br>
 - <b>mvn clean spring-boot:run </b> for running our applciation<br>
<br>

The whole project is packed into a Docker container --> look into Docker file for specification!

<hr>
### Security Aspects of Application
<br>
1) We can use either baseAuth using a user/name pass in application.properties mapped from Secrets Manager <br>
2) JWT using a jwt library for the token validation - > prod set should have an authorization server deployed as a standalone. <br><br>
### Running SSO on minishift
<br>
Running as admin on mini<br>
1) Once the minishift installed run <b>minishift ssh </b><br>
2) <b>sudo su -</b> <br>
3) <b>export KUBECONFIG=/var/lib/minishift/base/openshift-apiserver/admin.kubeconfig PATH="$PATH:/var/lib/minishift/bin"</b><br>
4) <b>oc whoami</b><br>
5) <b>oc adm policy add-cluster-role-to-user cluster-admin admin</b><br>
6) <b>exit</b> <br>
7) <b>exit</b><br>
..................<br>
8) <b>eval $(minishift oc-env)</b><br>
9) <b>oc login -u admin -p admin</b><br>
<i>Check if you are the admin</i><br>
10) <b>oc whoami</b><br>
