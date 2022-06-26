package com.example.autherjava.respon;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponPage {
    Boolean status ;
    String message ;
    Integer actPage ;
    Integer totalPage ;
    Object data ;

 public ResponPage(Integer actPage)
 {
     this.actPage = actPage ;
 }
    @Override
    public String toString() {
        return "{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", actPage=" + actPage +
                ", totalPage=" + totalPage +
                ", data=" + data +
                '}';
    }
}
