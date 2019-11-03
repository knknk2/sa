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

import { IM2mDriver, defaultValue } from 'app/shared/model/m-2-m-driver.model';

export const ACTION_TYPES = {
  SEARCH_M2MDRIVERS: 'm2mDriver/SEARCH_M2MDRIVERS',
  FETCH_M2MDRIVER_LIST: 'm2mDriver/FETCH_M2MDRIVER_LIST',
  FETCH_M2MDRIVER: 'm2mDriver/FETCH_M2MDRIVER',
  CREATE_M2MDRIVER: 'm2mDriver/CREATE_M2MDRIVER',
  UPDATE_M2MDRIVER: 'm2mDriver/UPDATE_M2MDRIVER',
  DELETE_M2MDRIVER: 'm2mDriver/DELETE_M2MDRIVER',
  RESET: 'm2mDriver/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IM2mDriver>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type M2mDriverState = Readonly<typeof initialState>;

// Reducer

export default (state: M2mDriverState = initialState, action): M2mDriverState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_M2MDRIVERS):
    case REQUEST(ACTION_TYPES.FETCH_M2MDRIVER_LIST):
    case REQUEST(ACTION_TYPES.FETCH_M2MDRIVER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_M2MDRIVER):
    case REQUEST(ACTION_TYPES.UPDATE_M2MDRIVER):
    case REQUEST(ACTION_TYPES.DELETE_M2MDRIVER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_M2MDRIVERS):
    case FAILURE(ACTION_TYPES.FETCH_M2MDRIVER_LIST):
    case FAILURE(ACTION_TYPES.FETCH_M2MDRIVER):
    case FAILURE(ACTION_TYPES.CREATE_M2MDRIVER):
    case FAILURE(ACTION_TYPES.UPDATE_M2MDRIVER):
    case FAILURE(ACTION_TYPES.DELETE_M2MDRIVER):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_M2MDRIVERS):
    case SUCCESS(ACTION_TYPES.FETCH_M2MDRIVER_LIST): {
      const links = parseHeaderForLinks(action.payload.headers.link);

      return {
        ...state,
        loading: false,
        links,
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links),
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    }
    case SUCCESS(ACTION_TYPES.FETCH_M2MDRIVER):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_M2MDRIVER):
    case SUCCESS(ACTION_TYPES.UPDATE_M2MDRIVER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_M2MDRIVER):
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

const apiUrl = 'api/m-2-m-drivers';
const apiSearchUrl = 'api/_search/m-2-m-drivers';

// Actions

export const getSearchEntities: ICrudSearchAction<IM2mDriver> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_M2MDRIVERS,
  payload: axios.get<IM2mDriver>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`)
});

export const getEntities: ICrudGetAllAction<IM2mDriver> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_M2MDRIVER_LIST,
    payload: axios.get<IM2mDriver>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IM2mDriver> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_M2MDRIVER,
    payload: axios.get<IM2mDriver>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IM2mDriver> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_M2MDRIVER,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const updateEntity: ICrudPutAction<IM2mDriver> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_M2MDRIVER,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IM2mDriver> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_M2MDRIVER,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
