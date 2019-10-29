The following is a summary of what JWT, OAuth2 and Auth0 are and how they fit together.

## Jwt
JSON Web Token (JWT) is an open standard (RFC 7519) that defines a compact and self-contained way for securely transmitting information between parties as a JSON object. This information can be verified and trusted because it is digitally signed. JWTs can be signed using a secret (with the HMAC algorithm) or a public/private key pair using RSA or ECDSA.

**JWT is a token format.**

For signed tokens the information, though protected against tampering, is readable by anyone. **Do not put secret information** in the payload or header elements of a JWT unless it is encrypted.

JSON Web Tokens consist of three parts separated by dots (.), which are:
* Header - contains type and signing algo.
```json
{
  "alg": "HS256",
  "typ": "JWT"
}
```
* Payload - `claims` and data. There are 3 types of claims - registered, public and private. Eg:
```json
{
  "sub": "1234567890",
  "name": "Raj Saxena",
  "admin": true
}
```
* Signature - builds with the above 2 using `HMACSHA256(base64UrlEncode(header) + "." + base64UrlEncode(payload), secret)`

All 3 are clubbed together as  - `xxxxx.yyyyy.zzzzz` and sent in the header.

___
## OAuth 2.0

OAuth 2.0 is a protocol that allows a user to grant _limited access_ to their resources on one site, to another site, without having to expose their credentials. Eg: Login with Google, Github, LinkedIn, etc

**OAuth 2.0 defines an authorization protocol, i.e. specifies how tokens are transferred** OAuth can use JWT as a token.

No point in trying to rephrase what people have already explained well - [here](https://www.digitalocean.com/community/tutorials/an-introduction-to-oauth-2) and [here](https://auth0.com/docs/protocols/oauth2) are good articles on OAuth terms and how it works. 
More detailed reading at [https://oauth.net/2/]

___
## Auth0

Auth0 provides authentication and authorization as a service.
Basic flow:
* Connect an app to Auth0
* Specify the providers
* Auth0 will verify identity and send the info requested via scope to the app.

Use Auth0 when you don't want the hassle of managing login flows and/or [these](https://auth0.com/docs/getting-started/overview#why-use-auth0-) flows

___
### References
[jwt.io](https://jwt.io/introduction/)
[JWT registry](https://www.iana.org/assignments/jwt/jwt.xhtml)
[OAuth 2.0 RFC](https://tools.ietf.org/html/rfc6749)
[StackOverFlow - JWT vs OAuth](https://stackoverflow.com/questions/39909419/what-are-the-main-differences-between-jwt-and-oauth-authentication)


