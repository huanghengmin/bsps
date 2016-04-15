/sbin/iptables -t nat -D PREROUTING --dst $1 -p tcp -m tcp --dport $2 -j DNAT --to-destination $3:$4
/sbin/iptables -t nat -D POSTROUTING --dst $3 -p tcp -m tcp  --dport $4 -j SNAT --to-source $1
/sbin/iptables -t nat -D OUTPUT --dst $1 -p tcp -m tcp --dport $2 -j DNAT --to-destination $3:$4
