package com.example.diccionariolsc;

import data.Palabra;

public class OpcionJuego {
    private Palabra ref;
    private String op1, op2, op3, op4;

    public OpcionJuego(Palabra ref, String op1, String op2, String op3, String op4) {
        this.ref = ref;
        this.op1 = op1;
        this.op2 = op2;
        this.op3 = op3;
        this.op4 = op4;
    }

    public String getCorrecta(){
        return ref.getContenido();
    }
    public String getURL(){
        return ref.getUrl();
    }

    public OpcionJuego() {
    }

    public Palabra getRef() {
        return ref;
    }

    public void setRef(Palabra ref) {
        this.ref = ref;
    }

    public String getOp1() {
        return op1;
    }

    public void setOp1(String op1) {
        this.op1 = op1;
    }

    public String getOp2() {
        return op2;
    }

    public void setOp2(String op2) {
        this.op2 = op2;
    }

    public String getOp3() {
        return op3;
    }

    public void setOp3(String op3) {
        this.op3 = op3;
    }

    public String getOp4() {
        return op4;
    }

    public void setOp4(String op4) {
        this.op4 = op4;
    }
}
