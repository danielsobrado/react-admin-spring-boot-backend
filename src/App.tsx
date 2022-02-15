import React, { useState, useEffect } from 'react';
import { fetchUtils, Admin, Resource } from 'react-admin';
import polyglotI18nProvider from 'ra-i18n-polyglot';

import './App.css';

import authProvider from './authProvider';
import themeReducer from './themeReducer';
import { Login, Layout } from './layout';
import { Dashboard } from './dashboard';
import customRoutes from './routes';
import englishMessages from './i18n/en';

import visitors from './visitors';
import orders from './orders';
import products from './products';
import invoices from './invoices';
import categories from './categories';
import reviews from './reviews';

// import dataProviderFactory from './dataProvider';
// import fakeServerFactory from './fakeServer';
import jsonSpringBootProvider from './lib/ra-data-springboot-rest';
import simpleRestProvider from 'ra-data-simple-rest';

const i18nProvider = polyglotI18nProvider(locale => {
    if (locale === 'fr') {
        return import('./i18n/fr').then(messages => messages.default);
    }

    // Always fallback on english
    return englishMessages;
}, 'en');

const App = () => {
    // const [dataProvider, setDataProvider] = useState(null);

    // useEffect(() => {
    //     let restoreFetch;

    //     const fetchDataProvider = async () => {
    //         restoreFetch = await fakeServerFactory(
    //             process.env.REACT_APP_DATA_PROVIDER
    //         );

    //         setDataProvider(
    //             await dataProviderFactory(process.env.REACT_APP_DATA_PROVIDER)
    //         );
    //     };

    //     fetchDataProvider();

    //     return restoreFetch;
    // }, []);

    // if (!dataProvider) {
    //     return (
    //         <div className="loader-container">
    //             <div className="loader">Loading...</div>
    //         </div>
    //     );
    // }

    // const dataProvider = jsonSpringBootProvider;

    const fetchJson = (url:any, options:any = {}) => {
        if (!options.headers) {
            options.headers = new Headers({ Accept: 'application/json' });
        }
        // add your own headers here
        options.headers.set('Access-Control-Expose-Headers', 'X-Total-Count');
        options.headers.set("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
        options.headers.set('Access-Control-Allow-Origin', '*');
        options.headers.set('X-Authorization', 'Bearer '+localStorage.getItem('token'));
        console.log("token from local: "+localStorage.getItem('token'));
        return fetchUtils.fetchJson(url, options);
    }
    const dataProvider = simpleRestProvider('http://localhost:8080/api/v1', fetchJson);

    return (
        <Admin
            title=""
            dataProvider={dataProvider}
            customReducers={{ theme: themeReducer }}
            customRoutes={customRoutes}
            authProvider={authProvider}
            dashboard={Dashboard}
            loginPage={Login}
            layout={Layout}
            i18nProvider={i18nProvider}
        >
            <Resource name="customers" {...visitors} />
            <Resource
                name="commands"
                {...orders}
                options={{ label: 'Orders' }}
            />
            <Resource name="invoices" {...invoices} />
            <Resource name="products" {...products} />
            <Resource name="categories" {...categories} />
            <Resource name="reviews" {...reviews} />
        </Admin>
    );
};

export default App;
