package com.crio.shorturl;
import java.util.*;

public class XUrlImpl implements XUrl{

    private HashMap<String, String> shortmap = new HashMap<String, String>();
    private HashMap<String, String> revshortmap = new HashMap<String, String>();
    private HashMap<String, Integer> hitcountmap = new HashMap<String, Integer>();

    private String generateCHAR(){
        //generating random string of length 9
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 9) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }
    public boolean startsWithSubstring(String text, String keyword) {
        for (int i = 0; i < keyword.length(); i++) {
              if (text.toLowerCase().charAt(i) != keyword.toLowerCase().charAt(i)) {
            return false;
              } else if (i == keyword.length() - 1) {
                  
                  return true;
                }   
         }
        return false;
    }

    public void delete_hitcountmap(String Url){
        hitcountmap.remove(Url);
    }

    public String registerNewUrl(String longUrl){

        if(shortmap.containsKey(longUrl)){
            return shortmap.get(longUrl);
        }
        else{
            String shortUrl="http://short.url/"+generateCHAR();
           
            shortmap.put(longUrl, shortUrl);
            revshortmap.put(shortUrl,longUrl);

            return shortUrl;
        }

    } 

    public String getUrl(String shortUrl){
        if(shortmap.containsKey(shortUrl)){
            if(hitcountmap.containsKey(shortUrl)){
                int ip=hitcountmap.get(shortUrl);
                delete_hitcountmap(shortUrl);
                hitcountmap.put(shortUrl, ip+1);
            }else{
                hitcountmap.put(shortUrl, 1);
            }
            return shortUrl;

        }else{
            String longUrl=revshortmap.get(shortUrl);
            if(hitcountmap.containsKey(longUrl)){
                int ip=hitcountmap.get(longUrl);
                delete_hitcountmap(longUrl);
                hitcountmap.put(longUrl, ip+1);
            }else{
                hitcountmap.put(longUrl, 1);
            }
            return longUrl;
        }
    }

    public String delete(String longUrl){
        String shortp=shortmap.get(longUrl);
        shortmap.remove(longUrl);
        revshortmap.remove(shortp);
        return shortp;
    }

    public String registerNewUrl(String longUrl, String shortUrl){
        if(revshortmap.containsKey(shortUrl) || !startsWithSubstring(shortUrl, "http://short.url/") || shortmap.containsKey(longUrl)){
            return null;
        }

        shortmap.put(longUrl, shortUrl);
        revshortmap.put(shortUrl,longUrl);


        return shortUrl;
        
    }

    public Integer getHitCount(String longUrl){
        if(!hitcountmap.containsKey(longUrl)){
            return 0;
        }
        return hitcountmap.get(longUrl);
    }





}