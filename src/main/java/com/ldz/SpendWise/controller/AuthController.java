package com.ldz.SpendWise.controller;

import com.ldz.SpendWise.service.AuthService;
import com.ldz.SpendWise.service.data.AuthenticateRequest;
import com.ldz.SpendWise.service.data.AuthenticateResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
@Validated
@Tag(name = "00 Authentication", description = "List of Authentication interfaces")
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "Prijava korisnika na sistem", description = """
                    
            <p>Primer tela zahteva za access token (JSON format):</p>
            <pre>
            <code>{
              "username": "administrator",
              "password": "P@$$w0rd",
              "grantType": "ACCESS"
            }
            </code>
            </pre>
                        
            <p>Primer tela zahteva za access token (XML format):</p>
            <pre>
            <code>&lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?&gt;
                &lt;AuthenticateRequest&gt;
                &lt;username&gt;administrator&lt;/username&gt;
                &lt;password&gt;P@$$w0rd&lt;/password&gt;
                &lt;grantType&gt;ACCESS&lt;/grantType&gt;
            &lt;/AuthenticateRequest&gt;
            </code>
            </pre>
                                    
            <p>Primer tela zahteva za refresh token:</p>
            <pre>
            <code>{
              "grantType": "REFRESH",
              "refreshToken": "...SOME_TOKEN_STRING...."               
            }
            </code>
            </pre>""")
    @PostMapping(value = "/authenticate",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<AuthenticateResponse> authenticate(@RequestBody AuthenticateRequest authenticateRequest) throws Exception {
        AuthenticateResponse authenticateResponse = authService.authenticate(authenticateRequest);
        if (authenticateResponse.getException() != null) {
            throw authenticateResponse.getException();
        }
        return new ResponseEntity<>(authenticateResponse, HttpStatus.OK);
    }
}
