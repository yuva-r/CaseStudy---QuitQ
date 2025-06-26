import { createContext, useState } from 'react';

const AuthContext = createContext({});
//creates context object initially empty
//creating component calles auth provider
export const AuthProvider = ({ children }) => {
    //state var-->later can store access token
    const [auth, setAuth] = useState(null);

    return (
        <AuthContext.Provider value={{ auth, setAuth }}>
            {children}
        </AuthContext.Provider>
    );
    //wrapping
}

export default AuthContext;