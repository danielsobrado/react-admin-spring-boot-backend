// import inMemoryJWT from './inMemoryJWT';
import { FormValues } from './layout/Login';

const authProvider = {
    login: ( creds : FormValues ): Promise<any> => {
        console.log('login', creds);
        localStorage.setItem('username', creds.username ?? '');
        const request = new Request('http://localhost:8080/api/v1/auth/login', {
            method: 'POST',
            body: JSON.stringify(creds),
            headers: new Headers({ 'Content-Type': 'application/json' }),
            credentials: 'include',
        });
        // inMemoryJWT.setRefreshTokenEndpoint('http://localhost:8080/api/v1/refresh-token');
        return fetch(request)
            .then((response) => {
                if (response.status < 200 || response.status >= 300) {
                    throw new Error(response.statusText);
                }
                return response.json();
            })
            .then(({ token, tokenExpiry }) => {
                console.log('authProvider token: ' + token);
                localStorage.setItem('token', token);
                // return inMemoryJWT.setToken(token, tokenExpiry);
                return true;
            });
    },

    logout: () => {
        console.log('logout');
        const request = new Request('http://localhost:8080/api/v1/logout', {
            method: 'GET',
            headers: new Headers({ 'Content-Type': 'application/json' }),
            credentials: 'include',
        });
        // inMemoryJWT.ereaseToken();
        // localStorage.removeItem('token');

        return fetch(request).then(() => '/login');
    },

    checkAuth: () => {
        console.log('checkAuth');
        return localStorage.getItem('token') ? Promise.resolve() : Promise.reject();

        // return inMemoryJWT.waitForTokenRefresh().then(() => {
        //     return inMemoryJWT.getToken() ? Promise.resolve() : Promise.reject();
        // });
    },

    checkError: (error:any) => {
        const status = error.status;
        console.log('checkError: '+status);
        if (status === 401 || status === 403) {
            // inMemoryJWT.ereaseToken();
            // localStorage.removeItem('token');
            return Promise.reject();
        }
        return Promise.resolve();
    },

    getPermissions: () => {
        return localStorage.getItem('token') ? Promise.resolve() : Promise.reject();
        // return inMemoryJWT.waitForTokenRefresh().then(() => {
        //     return inMemoryJWT.getToken() ? Promise.resolve() : Promise.reject();
        // });
    },
};

export default authProvider; 