import axios from 'axios';
import {
  ICrudSearchAction,
  parseHeaderForLinks,
  loadMoreDataWhenScrolled,
  ICrudGetAction,
  ICrudGetAllAction,
  ICrudPutAction,
  ICrudDeleteAction
} from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IFields, defaultValue } from 'app/shared/model/fields.model';

export const ACTION_TYPES = {
  SEARCH_FIELDS: 'fields/SEARCH_FIELDS',
  FETCH_FIELDS_LIST: 'fields/FETCH_FIELDS_LIST',
  FETCH_FIELDS: 'fields/FETCH_FIELDS',
  CREATE_FIELDS: 'fields/CREATE_FIELDS',
  UPDATE_FIELDS: 'fields/UPDATE_FIELDS',
  DELETE_FIELDS: 'fields/DELETE_FIELDS',
  SET_BLOB: 'fields/SET_BLOB',
  RESET: 'fields/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IFields>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type FieldsState = Readonly<typeof initialState>;

// Reducer

export default (state: FieldsState = initialState, action): FieldsState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_FIELDS):
    case REQUEST(ACTION_TYPES.FETCH_FIELDS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_FIELDS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_FIELDS):
    case REQUEST(ACTION_TYPES.UPDATE_FIELDS):
    case REQUEST(ACTION_TYPES.DELETE_FIELDS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_FIELDS):
    case FAILURE(ACTION_TYPES.FETCH_FIELDS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_FIELDS):
    case FAILURE(ACTION_TYPES.CREATE_FIELDS):
    case FAILURE(ACTION_TYPES.UPDATE_FIELDS):
    case FAILURE(ACTION_TYPES.DELETE_FIELDS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_FIELDS):
    case SUCCESS(ACTION_TYPES.FETCH_FIELDS_LIST): {
      const links = parseHeaderForLinks(action.payload.headers.link);

      return {
        ...state,
        loading: false,
        links,
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links),
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    }
    case SUCCESS(ACTION_TYPES.FETCH_FIELDS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_FIELDS):
    case SUCCESS(ACTION_TYPES.UPDATE_FIELDS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_FIELDS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.SET_BLOB: {
      const { name, data, contentType } = action.payload;
      return {
        ...state,
        entity: {
          ...state.entity,
          [name]: data,
          [name + 'ContentType']: contentType
        }
      };
    }
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/fields';
const apiSearchUrl = 'api/_search/fields';

// Actions

export const getSearchEntities: ICrudSearchAction<IFields> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_FIELDS,
  payload: axios.get<IFields>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`)
});

export const getEntities: ICrudGetAllAction<IFields> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_FIELDS_LIST,
    payload: axios.get<IFields>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IFields> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_FIELDS,
    payload: axios.get<IFields>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IFields> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_FIELDS,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const updateEntity: ICrudPutAction<IFields> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_FIELDS,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IFields> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_FIELDS,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const setBlob = (name, data, contentType?) => ({
  type: ACTION_TYPES.SET_BLOB,
  payload: {
    name,
    data,
    contentType
  }
});

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
