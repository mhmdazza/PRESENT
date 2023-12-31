''' Present Algorithm 80bit Key By.Muhammad Ziyad Almalik & Muhammad Azza Ulin Nuha '''

#INPUT :
#    Plaintext : 64 Bit
#    Key : 80 Bit
#OUTPUT :
#    Ciphertext : 64 Bit

sbox=   [0xc,0x5,0x6,0xb,0x9,0x0,0xa,0xd,0x3,0xe,0xf,0x8,0x4,0x7,0x1,0x2]
sbox_i= [sbox.index(x) for x in range(16)]
pbox =  [0,16,32,48,1,17,33,49,2,18,34,50,3,19,35,51,
        4,20,36,52,5,21,37,53,6,22,38,54,7,23,39,55,
        8,24,40,56,9,25,41,57,10,26,42,58,11,27,43,59,
        12,28,44,60,13,29,45,61,14,30,46,62,15,31,47,63]
pbox_i= [pbox.index(x) for x in range(64)]

def generateroundkeys(key):
    roundkeys=[]
    for i in range(1,33):
            roundkeys.append(key >>16)
            #1. Shift
            key = ((key & (2**19-1)) << 61)^(key >> 19)
            #2. sbox
            key = (sbox[key >> 76] << 76)+(key & (2**76-1))
            #3. Salt
            key ^= i << 15
            print(hex((key)))
    return roundkeys

def ark(state,roundkey):
    #addroundkey
    return state^roundkey

def sboxlayer(state):
    output = 0
    for i in range(16):
        output += sbox[( state >> (i*4)) & 0xF] << (i*4)
    return output

def sboxlayer_dec(state):
    output = 0
    for i in range(16):
        output += sbox_i[( state >> (i*4)) & 0xF] << (i*4)
    return output

def player(state):
    output = 0
    for i in range(64):
        output += ((state >> i) & 0x01) << pbox[i]
    return output

def player_dec(state):
    output = 0
    for i in range(64):
        output += ((state >> i) & 0x01) << pbox_i[i]
    return output

def encrypt(rkey,state):
    for i in range (0,31):
        state=ark(state,rkey[i])
        state=sboxlayer(state)
        state=player(state)
    state=ark(state,rkey[31])
    return state

def decrypt(rkey,state):
    for i in range (31,0,-1):
        state=ark(state,rkey[i])
        state=player_dec(state)
        state=sboxlayer_dec(state)
    state=ark(state,rkey[0])
    return state

key=0x0 #masukkan kunci 80 bit
key=generateroundkeys(key)
#print(hex(key))
#cipher=encrypt(key,0x0000000000000000) #masukkan plaintext 64 bit
#plain=decrypt(key,0xe72c46c0f5945049) #masukkan ciphertext 64 bit
#print(hex(cipher))
#print(hex(plain))
