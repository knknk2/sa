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

import { IUm2oOwner, defaultValue } from 'app/shared/model/um-2-o-owner.model';

export const ACTION_TYPES = {
  SEARCH_UM2OOWNERS: 'um2oOwner/SEARCH_UM2OOWNERS',
  FETCH_UM2OOWNER_LIST: 'um2oOwner/FETCH_UM2OOWNER_LIST',
  FETCH_UM2OOWNER: 'um2oOwner/FETCH_UM2OOWNER',
  CREATE_UM2OOWNER: 'um2oOwner/CREATE_UM2OOWNER',
  UPDATE_UM2OOWNER: 'um2oOwner/UPDATE_UM2OOWNER',
  DELETE_UM2OOWNER: 'um2oOwner/DELETE_UM2OOWNER',
  RESET: 'um2oOwner/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IUm2oOwner>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type Um2oOwnerState = Readonly<typeof initialState>;

// Reducer

export default (state: Um2oOwnerState = initialState, action): Um2oOwnerState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_UM2OOWNERS):
    case REQUEST(ACTION_TYPES.FETCH_UM2OOWNER_LIST):
    case REQUEST(ACTION_TYPES.FETCH_UM2OOWNER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_UM2OOWNER):
    case REQUEST(ACTION_TYPES.UPDATE_UM2OOWNER):
    case REQUEST(ACTION_TYPES.DELETE_UM2OOWNER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_UM2OOWNERS):
    case FAILURE(ACTION_TYPES.FETCH_UM2OOWNER_LIST):
    case FAILURE(ACTION_TYPES.FETCH_UM2OOWNER):
    case FAILURE(ACTION_TYPES.CREATE_UM2OOWNER):
    case FAILURE(ACTION_TYPES.UPDATE_UM2OOWNER):
    case FAILURE(ACTION_TYPES.DELETE_UM2OOWNER):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_UM2OOWNERS):
    case SUCCESS(ACTION_TYPES.FETCH_UM2OOWNER_LIST): {
      const links = parseHeaderForLinks(action.payload.headers.link);

      return {
        ...state,
        loading: false,
        links,
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links),
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    }
    case SUCCESS(ACTION_TYPES.FETCH_UM2OOWNER):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_UM2OOWNER):
    case SUCCESS(ACTION_TYPES.UPDATE_UM2OOWNER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_UM2OOWNER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/um-2-o-owners';
const apiSearchUrl = 'api/_search/um-2-o-owners';

// Actions

export const getSearchEntities: ICrudSearchAction<IUm2oOwner> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_UM2OOWNERS,
  payload: axios.get<IUm2oOwner>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`)
});

export const getEntities: ICrudGetAllAction<IUm2oOwner> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_UM2OOWNER_LIST,
    payload: axios.get<IUm2oOwner>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IUm2oOwner> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_UM2OOWNER,
    payload: axios.get<IUm2oOwner>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IUm2oOwner> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_UM2OOWNER,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const updateEntity: ICrudPutAction<IUm2oOwner> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_UM2OOWNER,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IUm2oOwner> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_UM2OOWNER,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
