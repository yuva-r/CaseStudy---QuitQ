import React, { useContext } from 'react';
import AuthContext from '../context/AuthProvider';
import { Outlet, useLocation, Navigate } from 'react-router-dom';

// RequireAuth now accepts allowedRoles as prop
export const RequireAuth = ({ allowedRoles }) => {
    const { auth } = useContext(AuthContext);
    const location = useLocation();

    const userRole = auth?.user?.role;

    return (
        auth?.accessToken && allowedRoles.includes(userRole) ? (
            <Outlet />
        ) : auth?.accessToken ? (
            <Navigate to="/unauthorized" state={{ from: location }} replace />
        ) : (
            <Navigate to="/login" state={{ from: location }} replace />
        )
    );
};