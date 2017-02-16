variable "provider" {
      type = "map"
    default = {
        access_key = ""
        secret_key = ""
        region = "us-west-2"
		key_name = ""
		instance_type = "t2.micro"
		ami = "ami-5e63d13e"
		private_key = ""
		}
	}	