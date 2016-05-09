/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package enums;

/**
 *
 * @author fobm
 */
public enum PosicaoEnum {
    A(1),
    B(2),
    C(3),
    D(4),
    E(5),
    F(6),
    G(7),
    H(8),
    I(9),
    J(10),
    K(11),
    L(12),
    ESTEPE(0);
    
    int pos;
    
    PosicaoEnum(int pos){
        this.pos = pos;
    }
    
    public int getPos(){
        return pos;
    }
    
    public int getPos(String pos){
        
        return this.pos;
    }
    
}
