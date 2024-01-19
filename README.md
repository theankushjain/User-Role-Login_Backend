"User-Role-Login_Backend" is a backend boiler plate for a login system in Spring-Boot version 3.0.12

A PostGreSQL database named questiondb has been used. The credentials of which have been mentioned in application.properties

##Controller
- Only one controller named UserController is present
  - It exposes 7 endpoints namely auth/welcome, auth/getUsers, auth/getRoles, auth/addNewUser, auth/user/userProfile, auth/admin/adminProfle, auth/generateToken.
- Generate Token makes use of one service namely JwtService to generate token and two entities namely AuthRequest and JwtResponse
- JWTAuthFilter is a helper filter used to extract token from the incoming request header so that it can be used to extract username
- CustomUserDetailsService has also been used as a helper service in JwtAuthFilter to find user in database.
- As inbuilt UserDetailsService of Spring Security expects user in a particular format, CustomUserDetailsService builds it.
- Complete CRUD operations of Role and User are given
- User List also maps roles associated with users
- User Registration includes giving multiple roles to the user as well

-User-Role-Login_Frontend
  - Consists of Filters, Pagination and Sorting on Mat-Tables
  - Download in Excel Format is also given
  - Soft Transitions are given for Sidebar
  - Multi Level Menu is also there
