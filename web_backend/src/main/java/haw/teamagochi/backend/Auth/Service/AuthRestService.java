package haw.teamagochi.backend.Auth.Service;


import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import haw.teamagochi.backend.user.logic.UcManageUserImpl;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.Base64;


@Path("/auth")
public class AuthRestService {

    @Inject
    JsonWebToken jwt;

    @Inject
    UcManageUserImpl manageUser;

    @GET
    @Path("/UserId")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean createUser(){
      String token = jwt.getRawToken();
      DecodedJWT decodedJWT = JWT.decode(token);
      String payloadJson = new String(Base64.getUrlDecoder().decode(decodedJWT.getPayload()), StandardCharsets.UTF_8);
      JSONObject payloadObject = new JSONObject(payloadJson);
      String id = payloadObject.getString("sid");
      manageUser.create(id);
      System.out.println(id);
      return true;
    }


}
