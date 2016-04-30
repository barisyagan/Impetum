ip=$(dig +short myip.opendns.com @resolver1.opendns.com)
echo "Server's IP address is $ip"
export Server_IP=$ip
echo "Done!"
