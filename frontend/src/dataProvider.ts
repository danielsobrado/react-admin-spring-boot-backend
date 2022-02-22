import { stringify } from 'query-string';
import {
    CREATE, DELETE,
    DELETE_MANY,
    fetchUtils, GET_LIST, GET_MANY,
    GET_MANY_REFERENCE, GET_ONE, UPDATE
} from 'react-admin';

const API_URL = 'http://localhost:8080/api/v1';

/**
 * @param {String} type One of the constants appearing at the top if this file, e.g. 'UPDATE'
 * @param {String} resource Name of the resource to fetch, e.g. 'posts'
 * @param {Object} params The Data Provider request params, depending on the type
 * @returns {Object} { url, options } The HTTP request parameters
 */

const convertDataProviderRequestToHTTP = (type : any, resource : any, params : any) : any => {
    switch (type) {
    case GET_LIST: {
        const { page, perPage } = params.pagination;
        const { field, order } = params.sort;
        const query = {
            sort: JSON.stringify([field, order]),
            range: JSON.stringify([page - 1,perPage]),
            filter: JSON.stringify(params.filter),
        };
        console.log("GET_LIST url: " + `${API_URL}/${resource}?${stringify(query)}`);
        return { url: `${API_URL}/${resource}?${stringify(query)}` };
    }
    case GET_ONE:
        console.log("GET_ONE url: " + `${API_URL}/${resource}/${params.id}` );
        return { url: `${API_URL}/${resource}/${params.id}` };
    case GET_MANY: {

        let objs = params.ids;
        let realIds : (string | number)[]= [];
        for (let obj of objs) {
            realIds.push(obj.id);
        }

        const query = {
            filter: JSON.stringify({id: realIds}),
        };

        console.log("GET_MANY url: " + `${API_URL}/${resource}?${stringify(query)}` );
        return { url: `${API_URL}/${resource}?${stringify(query)}` };
    }
    case GET_MANY_REFERENCE: {
        const { page, perPage } = params.pagination;
        const { field, order } = params.sort;
        const query = {
            sort: JSON.stringify([field, order]),
            range: JSON.stringify([page - 1 , perPage]),
            filter: JSON.stringify({ ...params.filter, [params.target]: params.id }),
        };

        let url : {url : string} = { url: `${API_URL}/${resource}?${stringify(query)}` };
        console.log("GET_MANY url: " + `${API_URL}/${resource}/${params.id}` );
        return url;
    }
    case UPDATE:
        return {
            url: `${API_URL}/${resource}/${params.id}`,
            // options: { method: 'PUT', body: JSON.stringify( upsertOneRequestHelper(params.data, keysContainingFilePaths, resource)) },
        };
    case CREATE:
        return {
            url: `${API_URL}/${resource}`,
            // options: { method: 'POST', body: JSON.stringify( upsertOneRequestHelper(params.data, keysContainingFilePaths, resource)) },
        };
    case DELETE:
        return {
            url: `${API_URL}/${resource}/${params.id}`,
            options: { method: 'DELETE' },
        };
    case DELETE_MANY:
        return {
            url: `${API_URL}/${resource}/${params.ids[0]}`,
            options: { method: 'DELETE' },
        };
    default:
        throw new Error(`Unsupported fetch action type ${type}`);
    }
};

// const upsertOneRequestHelper = (row : any, fileKeys : any, resource : any)  : any => {
//     let request = {};
//     Object.keys(row).forEach(function(key) {
//         request[key] = row[key];
//     });
//     return request;

// }


const getOneRowResponseHelper = (row : any, rowInList : any, resource : any) => {
    let result : any = {};

   
    Object.keys(row).forEach(function(key) {
        result[key] = row[key];
    });
    return result;
};


const prepareGetListResponse = (rows : any, resource : any) : any => {

    let result =  rows.map(function (row: any) {
        return getOneRowResponseHelper(row, true, resource);
    });
    return result;
}

/**
 * @param {Object} response HTTP response from fetch()
 * @param {String} type One of the constants appearing at the top if this file, e.g. 'UPDATE'
 * @param {String} resource Name of the resource to fetch, e.g. 'posts'
 * @param {Object} params The Data Provider request params, depending on the type
 * @returns {Object} Data Provider response
 */
// const convertHTTPResponseToDataProvider = (response : any, type : any, resource : any, params : any) : any => {
//     const { headers, json } = response;
//     switch (type) {
       
//         case GET_LIST:
//         case GET_MANY:
//             return {
//                 data: prepareGetListResponse(json.content.map((x: any) => x), resource),
//                 total: parseInt(json.totalElements, 10)
//             };
//         case CREATE:
//             return { data: { ...params.data, id: json.id } };
//         default:
//             return { data: getOneRowResponseHelper(json, keysContainingFilePaths, false, resource)};
//     }
// };

const convertHTTPResponseToDataProvider = (response : any, type : any, resource : any, params : any) : any => {
    const { headers, json } = response;
    switch (type) {
        case GET_LIST: {
            console.log("GET_LIST reply:" + json.content);
            return {
                data: json.content.at(0).content,
                total: parseInt(json.content.at(0).total, 10),
            };
        }
        case GET_MANY:
            console.log("GET_MANY reply:" + json);
            return {
                data: json.content.at(0).content,
                total: parseInt(json.content.at(0).total, 10),
            };
        case GET_ONE:
            return {
                data: json
            };
        case CREATE:
            return { data: { ...params.data, id: json.id } };
        default:
            return { data: json };
    }
};

/**
 * @param {string} type Request type, e.g GET_LIST
 * @param {string} resource Resource name, e.g. "posts"
 * @param {Object} payload Request parameters. Depends on the request type
 * @returns {Promise} the Promise for response
 */
export default (type : any, resource : any, params : any) : any => {
    const { fetchJson } = fetchUtils;
    let { url, options } = convertDataProviderRequestToHTTP(type, resource, params);
    if (!options) {
        options = {};
    }

    if (!options.headers) {
        options.headers = new Headers({ Accept: 'application/json' });
    }

    let token = localStorage.getItem('token');
    options.headers.append('X-Authorization','Bearer ' + token);
    return fetchJson(url, options)
        .then((response: any) => convertHTTPResponseToDataProvider(response, type, resource, params));
};