/* A terraform template to create an instance and install docker in it */

provider "aws" {
    region = "${var.provider["region"]}"
	access_key="${var.provider["access_key"]}"
	secret_key="${var.provider["secret_key"]}"
}

	
resource "aws_security_group" "docker_host_sg" {
	
    name = "docker_host_sg"
    description = "security group for docker host"
	
	ingress {
        from_port = 22
        to_port = 22
        protocol= "tcp"
        cidr_blocks = ["0.0.0.0/0"]
    }
		ingress {
        from_port = 8080
        to_port = 8080
        protocol= "tcp"
        cidr_blocks = ["0.0.0.0/0"]
    }
		ingress {
        from_port = 443
        to_port = 443
        protocol= "tcp"
        cidr_blocks = ["0.0.0.0/0"]
    }
	 
		ingress {
        from_port = 80
        to_port = 80
        protocol= "tcp"
        cidr_blocks = ["0.0.0.0/0"]
    }
	    ingress {
        from_port = 3306
        to_port = 3306
        protocol= "tcp"
        cidr_blocks = ["0.0.0.0/0"]
    }
	   egress {
        from_port = 0
        to_port = 65535
        protocol= "tcp"
        cidr_blocks = ["0.0.0.0/0"]
    }
}

resource "aws_instance" "docker_host"  {   
    
	ami = "${var.provider["ami"]}"
    instance_type = "${var.provider["instance_type"]}"
	key_name ="${var.provider["key_name"]}"
	security_groups= ["${aws_security_group.docker_host_sg.name}"]
	root_block_device 
        {
          
		  volume_type = "gp2"
          volume_size = 15
          delete_on_termination = "true"
        }
	
 connection {
               type = "ssh"
               user = "ubuntu"
               port = 22
               private_key = "${file("${var.provider["private_key_path"]}")}"
               timeout = "15m"
            }

   provisioner "remote-exec" {
        inline = [
         "sudo apt-get update",
		 "sudo apt-get install docker.io -y",
		 "sudo apt-get install mysql-client -y",
		 "sudo apt-get install maven -y",
		 "sudo docker pull jenkins",
		 "sudo mkdir /var/jenkins-home",
		 "sudo docker run -d --name crns-jenkins -p 8080:8080 -p 50000:50000 -v /var/jenkins-home:/var/jenkins_home -u root jenkins"
        ]
    }   

 tags {
        Name = "docker_host_CRNS"
       }		
}

resource "aws_eip" "docker_host_pub_ip" {
    
	instance = "${aws_instance.docker_host.id}"
 
 }