/sbin/iptables -t nat -A PREROUTING --dst $1 -p udp -m udp --dport $2 -j DNAT --to-destination $3:$4
/sbin/iptables -t nat -A POSTROUTING --dst $3 -p udp -m udp  --dport $4 -j SNAT --to-source $1
/sbin/iptables -t nat -A OUTPUT --dst $1 -p udp -m udp --dport $2 -j DNAT --to-destination $3:$4
