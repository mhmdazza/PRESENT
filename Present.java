/*
 * PRESENT ALGORITHM BY MUHAMMAD ZIYAD & MUHAMMAD AZZA ULIN NUHA
 */
package present;

//import java.util.Arrays;

/**
 *
 * @author Thinkpad
 */
public class Present {

    /**
     * @param args the command line arguments
     */
    
    private static int[] keyaddition(int[] plain,int round,int[][] kunci){
       	for(int i = 0;i<4;i++){
            plain[i] ^= kunci[round][i];
        }
    return plain;
    }
    
    private static int[] sbox(int[] plain){
    	int SBOX[] =    
                    {0xc,0x5,0x6,0xb,
                    0x9,0x0,0xa,0xd,
                    0x3,0xe,0xf,0x8,
                    0x4,0x7,0x1,0x2};
        for(int i=0;i<4;i++){
            int temp=0;
            temp |=(SBOX[(plain[i]&0xF)]);
            temp |=(SBOX[(plain[i]>>=4)&0xF]<<4);
            temp |=(SBOX[(plain[i]>>=4)&0xF]<<8);
            temp |=(SBOX[(plain[i]>>=4)&0xF]<<12);
            plain[i]=temp;
        }
        return plain;
        }
    
    private static int[] sboxinv(int[] plain){
    	int SBOXInv[] = 
                    {0x5,0xe,0xf,0x8,
                    0xc,0x1,0x2,0xd,
                    0xb,0x4,0x6,0x3,
                    0x0,0x7,0x9,0xa};
        for(int i=0;i<4;i++){
            int temp=0;
            temp |=(SBOXInv[(plain[i]&0xF)]);
            temp |=(SBOXInv[(plain[i]>>=4)&0xF]<<4);
            temp |=(SBOXInv[(plain[i]>>=4)&0xF]<<8);
            temp |=(SBOXInv[(plain[i]>>=4)&0xF]<<12);
            plain[i]=temp;
        }
        return plain;
    }
    
    private static int[] permutation(int[]Plain){
       	int state[]=new int[4];
        for(int i=0 ; i<4 ; i++){
            int a=0;
            a ^= (Plain[0]<<i)&0x8000;
            a ^= ((Plain[0]<<(4+i))&0x8000) >>1;
            a ^= ((Plain[0]<<(8+i))&0x8000) >>2;
            a ^= ((Plain[0]<<(12+i))&0x8000) >>3;
            a ^= ((Plain[1]<<i)&0x8000) >>4;
            a ^= ((Plain[1]<<(4+i))&0x8000) >>5;
            a ^= ((Plain[1]<<(8+i))&0x8000) >>6;
            a ^= ((Plain[1]<<(12+i))&0x8000) >>7;
            a ^= ((Plain[2]<<i)&0x8000) >>8;
            a ^= ((Plain[2]<<(4+i))&0x8000) >>9;
            a ^= ((Plain[2]<<(8+i))&0x8000) >>10;
            a ^= ((Plain[2]<<(12+i))&0x8000) >>11;
            a ^= ((Plain[3]<<i)&0x8000) >>12;
            a ^= ((Plain[3]<<(4+i))&0x8000) >>13;
            a ^= ((Plain[3]<<(8+i))&0x8000) >>14;
            a ^= ((Plain[3]<<(12+i))&0x8000) >>15;
            state[i] = a;
        }
        return state;
    }
    
    private static int[] permutationinv(int[] Plain){
        int state[]=new int[4];
        int i=0;
        for(int j=0 ; j<4 ; j++){
            int temp=0;
            temp |= (Plain[0]<<i)&0x8000;
            temp |= ((Plain[1]<<(i))&0x8000) >>1;
            temp |= ((Plain[2]<<(i))&0x8000) >>2;
            temp |= ((Plain[3]<<(i))&0x8000) >>3;	
            temp |= ((Plain[0]<<(1+i))&0x8000) >>4;
            temp |= ((Plain[1]<<(1+i))&0x8000) >>5;
            temp |= ((Plain[2]<<(1+i))&0x8000) >>6;
            temp |= ((Plain[3]<<(1+i))&0x8000) >>7;	
            temp |= ((Plain[0]<<(2+i))&0x8000) >>8;
            temp |= ((Plain[1]<<(2+i))&0x8000) >>9;
            temp |= ((Plain[2]<<(2+i))&0x8000) >>10;
            temp |= ((Plain[3]<<(2+i))&0x8000) >>11;
            temp |= ((Plain[0]<<(3+i))&0x8000) >>12;
            temp |= ((Plain[1]<<(3+i))&0x8000) >>13;
            temp |= ((Plain[2]<<(3+i))&0x8000) >>14;
            temp |= ((Plain[3]<<(3+i))&0x8000) >>15;
            state[j]=temp;
            i=i+4;
        }
        return state;
    }
    
    private static int[][] keyschedule(int[] Key,int[][] kunci){
        int temp;
        
        int SBOX[] =    
                    {0xc,0x5,0x6,0xb,
                    0x9,0x0,0xa,0xd,
                    0x3,0xe,0xf,0x8,
                    0x4,0x7,0x1,0x2};
        int key[]=new int [5];
        System.arraycopy(Key, 0, kunci[0], 0, 5);        
        for (int r=1;r<32;r++){
            key[0] = Key[4]>>3;
            key[0] |= (Key[3]&0x07)<<13;
            
            key[1] = Key[0]>>3;
            key[1] |= (Key[4]&0x07)<<13;
	
            key[2] = Key[1]>>3;
            key[2] |= (Key[0]&0x07)<<13;
		
            key[3] = Key[2]>>3;
            key[3] |= (Key[1]&0x07)<<13;
		
            key[4] = Key[3]>>3;
            key[4] |= (Key[2]&0x07)<<13;
	
            //sBox
            temp = key[0]>>12;
            key[0] &= 0x0fff;
            key[0] |= SBOX[temp]<<12;
            
            //Salt
            key[4] =key[4]^((r&1)<<15);
            key[3] =key[3]^(r>>1);
            
            Key[0] = key[0];
            Key[1] = key[1];
            Key[2] = key[2];
            Key[3] = key[3];
            Key[4] = key[4];
            System.arraycopy(Key, 0, kunci[r], 0, 5);
        }
    return kunci;
    } 
    
    private static int[] enkrip_present(int[] plain, int[] k){
        int kunci[][]=new int[32][5];        
        kunci=keyschedule(k,kunci);
        for (int i=0;i<31;i++){
            plain=keyaddition(plain,i,kunci);
//            for(int j=0;j<4;j++){
//                System.out.print(Integer.toHexString(plain[j]));
//            }
//            System.out.println();
            plain=sbox(plain);
            plain=permutation(plain);
        }
        plain=keyaddition(plain,31,kunci);
        return plain;
    }
    
    private static int[] dekrip_present(int[] cipher,int[] k){
        int kunci[][]=new int[32][5];
        kunci=keyschedule(k,kunci);
        for (int i=0;i<31;i++){
            int r=31-i;
            cipher=keyaddition(cipher,r,kunci);
//            for(int j=0;j<4;j++){
//                System.out.print(Integer.toHexString(cipher[j]));
//            }
//            System.out.println();
            cipher=permutationinv(cipher);
            cipher=sboxinv(cipher);
        }
        cipher=keyaddition(cipher,0,kunci);
        return cipher;
    }
        
    public static void main(String[] args) {
        int plain[]={0x0,0x0,0x0,0x0};
        int cipher[]={0x5579,0xc138,0x7b22,0x8445};
        int key[]={0x0,0x0,0x0,0x0,0x0};
        //cipher=enkrip_present(plain,key);
        plain=dekrip_present(cipher,key);
//          cek
//        for(int j=0;j<4;j++){
//            System.out.print(Integer.toHexString(plain[j]));
//        }
//        System.out.println();
    }
 }
 
