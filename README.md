# Mobile-EdgeCompute  

Mobile Edge Computing Hierarchical Model which has the mobile as the edge server and the  

cloud as the central server. The load balancing between the mobile devices acting   

as the edge servers is implemented.  

 
## Modules  

There are three modules in this System:-

### AndroidEdgeServer  

	#### Credentials for the Android hosted Web server - Username - admin / Password - 1234
	
	#### Credentials for the Web service running in the cloud - Username- admin /Password - 9292

The Android Application is an edge server that can be run with the following steps  

	1> Git clone the project  
	
	2> Navigate to the Mobile-EdgeCompute folder
	
	3> Open the AndroidEdgeServer project in Android Studio  
	
	4> The edge server will be started by default,and the edge server can  
	
	   be started and stopped in the UI of the application.  
	   
	5> Note down the IP address link generated in the Android application UI.  
	
	6> The web application started from the edge server can be accessed using   
	
	   the link generated in the UI, assuming that the other client device   
	   
	   accessing the web application is in the same network.   
	   
	7> Example of the Web Url: http://192.168.1.70:8080/home/  
	
	8> The Web Application Url on the UI will be http://192.168.1.70:8080 but  
	
	   make sure to add /home to it while accessing the application from the  
	   
	   client.   
	   
### Cloud Web Application Hosted in Aws   

	The following link provides the steps to deploy the service in the cloud. 
	
		https://github.com/karankotz/Mobile-EdgeCompute/tree/master/CloudService
	
### Load Balancer   

	The steps to configure the load balancer can be found at the following link 
	in the repository
	
		https://github.com/karankotz/Mobile-EdgeCompute/blob/master/Load%20Balancer/Readme.md
		
### Youtube Video Links 

	SmartCompute Hierarchical Cloud Analysis Application Demo: 
	
		https://youtu.be/65Y-AWTapV4
		
	SmartCompute Hierarchical Cloud Analysis Application Demo with Load Balancer: 
	
		https://youtu.be/RZIQKgXtx5k
		
### References

	https://github.com/piotrpolak/android-http-server
	
    https://www.haproxy.org/download/1.4/doc/configuration.txt
	
### Git Link

	https://github.com/karankotz/Mobile-EdgeCompute

		
	
	
	
	



