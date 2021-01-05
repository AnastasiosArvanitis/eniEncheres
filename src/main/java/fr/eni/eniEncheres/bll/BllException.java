package fr.eni.eniEncheres.bll;

public class BllException extends Exception{
    private static final long serialVersionUID = 1L;

    public BllException(){
        super();
    }
    public BllException(String message){
        super(message);
    }
    public BllException(String message, Throwable exception){
        super(message, exception);
    }
    @Override
    public String getMessage(){
        StringBuffer sb = new StringBuffer("Couche BLL - ");
        sb.append(super.getMessage());
        return sb.toString();
    }
}
