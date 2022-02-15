import inMemoryJWT from './inMemoryJWT';
import { FormValues } from './layout/Login';

const authProvider = {
    login: ( creds : FormValues ): Promise<any> => {
        // localStorage.setItem('username', creds.username);
        const request = new Request('http://localhost:8080/api/v1/auth/login', {
            method: 'POST',
            body: JSON.stringify(creds),
            headers: new Headers({ 'Content-Type': 'application/json' }),
            credentials: 'include',
        });
        inMemoryJWT.setRefreshTokenEndpoint('http://localhost:8080/api/v1/refresh-token');
        return fetch(request)
            .then((response) => {
                if (response.status < 200 || response.status >= 300) {
                    throw new Error(response.statusText);
                }
                return response.json();
            })
            .then(({ token, tokenExpiry }) => {
                localStorage.setItem('token', token);
                return inMemoryJWT.setToken(token, tokenExpiry);
            });
    },

    logout: () => {
        const request = new Request('http://localhost:8080/api/v1/logout', {
            method: 'GET',
            headers: new Headers({ 'Content-Type': 'application/json' }),
            credentials: 'include',
        });
        inMemoryJWT.ereaseToken();

        return fetch(request).then(() => '/login');
    },

    checkAuth: () => {
        return inMemoryJWT.waitForTokenRefresh().then(() => {
            return inMemoryJWT.getToken() ? Promise.resolve() : Promise.reject();
        });
    },

    checkError: (error:any) => {
        const status = error.status;
        if (status === 401 || status === 403) {
            inMemoryJWT.ereaseToken();
            return Promise.reject();
        }
        return Promise.resolve();
    },

    getPermissions: () => {
        return inMemoryJWT.waitForTokenRefresh().then(() => {
            return inMemoryJWT.getToken() ? Promise.resolve() : Promise.reject();
        });
    },
};

export default authProvider; 