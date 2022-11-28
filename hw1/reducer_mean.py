#!/usr/bin/python3

import sys

c, m = 0, 0
for line in sys.stdin:
    line = line.strip()
    ck, mk = line.split()
    ck, mk = int(ck), float(mk), float(vk)
    m = ((m*c)+(mk*ck))/(c+ck)
    c += ck

print(c, m)