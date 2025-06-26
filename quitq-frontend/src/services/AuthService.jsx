import axios from "axios"

const   BASE_URI="http://localhost:8080/api/authenticate"
 class AuthService{
    registerUser(user){
        return axios.post(BASE_URI+`/register`,user)

    }
    loginUser(user){
         return axios.post (BASE_URI+`/login`,user)
    }
 }
 export default new AuthService();