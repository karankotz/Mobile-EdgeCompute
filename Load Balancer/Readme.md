##Steps to Configure the HAProxy Load Balancer

  ### Login to the Linux System and run the following command in the terminal
		sudo apt-get install haproxy
	  
  ### Set the service to enabled by adding "ENABLED=1", by opening the haproxy 
	  file with the following command.
	  
		sudo nano /etc/default/haproxy
  
  ### Copy paste the following code into the haproxy.cfg file 
  
		sudo nano /etc/haproxy/haproxy.cfg 
		
      Code:
		
				frontend haproxy_in
				bind *:8080
				default_backend haproxy_http

		backend haproxy_http
				balance roundrobin
				mode http
				server edge1 192.168.1.70:8080 check
				server edge2 192.168.1.105:8080 check

		listen stats
				bind *:9000
				stats enable
				stats uri /haproxy_stats
				stats hide-version
				
  ### Reload the HAProxy service with the following command 

			service haproxy reload
			
  ### Access the system using the following URL format
  
			<http://IP-Adress-HAProxy-Running-System:8080/home/Login>
			
  ### Load-balancing Statastics can be found at URL
			
			<http://IP-Adress-HAProxy-Running-System:9000/haproxy_stats>
			
		
		
		
		
		
	
		
	  
	 