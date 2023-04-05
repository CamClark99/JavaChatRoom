<h1>CLI-based network distributed system</h1>
<img src="https://cdn-icons-png.flaticon.com/512/4505/4505323.png" width="80" height="80"/>

<h2>Requirements</h2>

<h3>Non Functional Requirements</h3>

<ul>
<li>Modularity using design patterns</li>
<li>JUnit based testing of the application</li>
<li>Fault Tolerance</li>
<li>Component based development</li>
</ul>


<h3>Functional Requirements</h3>

<h4>Server side Requirements</h4>
<ul>

<li>Before running the server, the listening port should be specified</li>
<li>The server should be able to handle multiple clients</li>
<li>The server should tell the first client that he is the coordinator</li>
<li>The server should check periodically if the coordinator is still online, and assign a new one if he is not</li>
<li>The server should update all clients on members that have disconnected</li>

</ul>

<h4>Client Side Requirements</h4>

<ul>
<li>Before running the client, the following information should be specified:
<ul>
<li>IP address of the server</li>
<li>Port of the server</li>
<li>Unique Username</li>
<li>Port to listen to</li>
<li>IP address of the client</li>
</ul>
</li>
<li>Client should be able to send public messages</li>
<li>Client should be able to send private messages</li>
</ul>

<h4>Additional features</h4>
<ul>
<li>Bad word checker was implemented, to check for inappropriate language</li>
<li>History writer was implemented to record all the broadcast messages</li>
</ul>









