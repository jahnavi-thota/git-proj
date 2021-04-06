import axios from 'axios';
const User_API_BASE_URL = "http://localhost:8080/api/v1/users";
class UserService{
    viewUsers(){
        return axios.get(User_API_BASE_URL);
    }
    addUser(user){
        return axios.post(User_API_BASE_URL,user);
    }
    deleteUser(userId){
        return axios.delete(User_API_BASE_URL+'/'+ userId);
    }
    updateUser(user, userId){
        return axios.put(User_API_BASE_URL +'/'+ userId, user);
    }
    getUserById(userId){
        return axios.get(User_API_BASE_URL +'/'+  userId);
    }
}
export default new UserService()