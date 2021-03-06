## Camel + Spring + SOAP Transformation using a REST Facade
<br>
The project uses maven as a tool for building our project.
<br>
 - <b>mvn install</b> for installing all dependencies<br>
 - <b>mvn clean spring-boot:run </b> for running our applciation<br>
<br>

The whole project is packed into a Docker container --> look into Docker file for specification!

<hr>
<h4>Security Aspects of Application</h4>
<br>
1) We can use either baseAuth using a user/name pass in application.properties mapped from Secrets Manager <br>
2) JWT using a jwt library for the token validation - > prod set should have an authorization server deployed as a standalone. <br><br>
<h4>Running SSO on minishift</h4>

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
<b>https://github.com/jannikhuels/minishift-redhatsso</b> Contains files needed to run  SSO on minishift<br>
<b>oc get route sso -o jsonpath='http://{.spec.host}/auth/admin/'</b> will return SSO admin panel url on minishift<br>
Make sure to have a DNS started as well => minishift config set local-proxy true and  minishift dns start <br><br>
The url for access endpoints will visible in the admin console : <b>exmp = > http://sso-sso-poc.192.168.99.100.nip.io/auth/realms/{realms}/.well-known/openid-configuration</b><br>
In order to obtain a token send a request to : http://sso-sso-poc.192.168.99.100.nip.io/auth/realms/{realm}/protocol/openid-connect/token <br>
```
curl -X POST 'http://localhost:8080/auth/realms/{realms}/protocol/openid-connect/token' \
 --header 'Content-Type: application/x-www-form-urlencoded' \
 --data-urlencode 'grant_type=password' \
 --data-urlencode 'client_id=springboot-microservice' \
 --data-urlencode 'client_secret=xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxx' \
 --data-urlencode 'username=employee1' \
 --data-urlencode 'password=mypassword'
```
<b> MAKE SURE TO CHECK THE CONFIGURATATION FOR TIMEZONE ON THE SSO SERVER , OTHERWISE WILL ALWAYS RESULT TO 401 </b><br>
<b>For DNS configuration refer to https://docs.okd.io/3.11/minishift/using/experimental-features.html</b>
<h4>AMQ Artemis for minishift</h4>
Installation steps can be found at <b>https://github.com/amq-io/hello-world-jms-openshift</b> <br>
After you run installation make sure to create a dir - > sudo touch /etc/resolver/nip.io<br>
Then you have to add an entry with a minishift IP - > nameserver 192.168.99.100 <br>

http://<sender-host>/api/send <br>
http://<receiver-host>/api/receive <br>


<h4>VirtualBox installation </h4>
For those suffering on MAC OS <br>
Do the <b>recovery mode restart </b>, on restart press CMD + R<br>
Open terminal and add a consent for Oracle<br>
<b>spctl kext-consent add VB5E2TV963</b><br>
Reboot.<br>
The installation will finally work.<br><br>



