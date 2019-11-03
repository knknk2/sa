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

import { IUm2oCar, defaultValue } from 'app/shared/model/um-2-o-car.model';

export const ACTION_TYPES = {
  SEARCH_UM2OCARS: 'um2oCar/SEARCH_UM2OCARS',
  FETCH_UM2OCAR_LIST: 'um2oCar/FETCH_UM2OCAR_LIST',
  FETCH_UM2OCAR: 'um2oCar/FETCH_UM2OCAR',
  CREATE_UM2OCAR: 'um2oCar/CREATE_UM2OCAR',
  UPDATE_UM2OCAR: 'um2oCar/UPDATE_UM2OCAR',
  DELETE_UM2OCAR: 'um2oCar/DELETE_UM2OCAR',
  RESET: 'um2oCar/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IUm2oCar>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type Um2oCarState = Readonly<typeof initialState>;

// Reducer

export default (state: Um2oCarState = initialState, action): Um2oCarState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_UM2OCARS):
    case REQUEST(ACTION_TYPES.FETCH_UM2OCAR_LIST):
    case REQUEST(ACTION_TYPES.FETCH_UM2OCAR):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_UM2OCAR):
    case REQUEST(ACTION_TYPES.UPDATE_UM2OCAR):
    case REQUEST(ACTION_TYPES.DELETE_UM2OCAR):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_UM2OCARS):
    case FAILURE(ACTION_TYPES.FETCH_UM2OCAR_LIST):
    case FAILURE(ACTION_TYPES.FETCH_UM2OCAR):
    case FAILURE(ACTION_TYPES.CREATE_UM2OCAR):
    case FAILURE(ACTION_TYPES.UPDATE_UM2OCAR):
    case FAILURE(ACTION_TYPES.DELETE_UM2OCAR):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_UM2OCARS):
    case SUCCESS(ACTION_TYPES.FETCH_UM2OCAR_LIST): {
      const links = parseHeaderForLinks(action.payload.headers.link);

      return {
        ...state,
        loading: false,
        links,
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links),
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    }
    case SUCCESS(ACTION_TYPES.FETCH_UM2OCAR):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_UM2OCAR):
    case SUCCESS(ACTION_TYPES.UPDATE_UM2OCAR):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_UM2OCAR):
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

const apiUrl = 'api/um-2-o-cars';
const apiSearchUrl = 'api/_search/um-2-o-cars';

// Actions

export const getSearchEntities: ICrudSearchAction<IUm2oCar> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_UM2OCARS,
  payload: axios.get<IUm2oCar>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`)
});

export const getEntities: ICrudGetAllAction<IUm2oCar> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_UM2OCAR_LIST,
    payload: axios.get<IUm2oCar>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IUm2oCar> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_UM2OCAR,
    payload: axios.get<IUm2oCar>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IUm2oCar> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_UM2OCAR,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const updateEntity: ICrudPutAction<IUm2oCar> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_UM2OCAR,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IUm2oCar> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_UM2OCAR,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
