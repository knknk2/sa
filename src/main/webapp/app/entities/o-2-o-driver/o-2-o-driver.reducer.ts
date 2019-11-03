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

import { IO2oDriver, defaultValue } from 'app/shared/model/o-2-o-driver.model';

export const ACTION_TYPES = {
  SEARCH_O2ODRIVERS: 'o2oDriver/SEARCH_O2ODRIVERS',
  FETCH_O2ODRIVER_LIST: 'o2oDriver/FETCH_O2ODRIVER_LIST',
  FETCH_O2ODRIVER: 'o2oDriver/FETCH_O2ODRIVER',
  CREATE_O2ODRIVER: 'o2oDriver/CREATE_O2ODRIVER',
  UPDATE_O2ODRIVER: 'o2oDriver/UPDATE_O2ODRIVER',
  DELETE_O2ODRIVER: 'o2oDriver/DELETE_O2ODRIVER',
  RESET: 'o2oDriver/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IO2oDriver>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type O2oDriverState = Readonly<typeof initialState>;

// Reducer

export default (state: O2oDriverState = initialState, action): O2oDriverState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_O2ODRIVERS):
    case REQUEST(ACTION_TYPES.FETCH_O2ODRIVER_LIST):
    case REQUEST(ACTION_TYPES.FETCH_O2ODRIVER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_O2ODRIVER):
    case REQUEST(ACTION_TYPES.UPDATE_O2ODRIVER):
    case REQUEST(ACTION_TYPES.DELETE_O2ODRIVER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_O2ODRIVERS):
    case FAILURE(ACTION_TYPES.FETCH_O2ODRIVER_LIST):
    case FAILURE(ACTION_TYPES.FETCH_O2ODRIVER):
    case FAILURE(ACTION_TYPES.CREATE_O2ODRIVER):
    case FAILURE(ACTION_TYPES.UPDATE_O2ODRIVER):
    case FAILURE(ACTION_TYPES.DELETE_O2ODRIVER):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_O2ODRIVERS):
    case SUCCESS(ACTION_TYPES.FETCH_O2ODRIVER_LIST): {
      const links = parseHeaderForLinks(action.payload.headers.link);

      return {
        ...state,
        loading: false,
        links,
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links),
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    }
    case SUCCESS(ACTION_TYPES.FETCH_O2ODRIVER):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_O2ODRIVER):
    case SUCCESS(ACTION_TYPES.UPDATE_O2ODRIVER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_O2ODRIVER):
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

const apiUrl = 'api/o-2-o-drivers';
const apiSearchUrl = 'api/_search/o-2-o-drivers';

// Actions

export const getSearchEntities: ICrudSearchAction<IO2oDriver> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_O2ODRIVERS,
  payload: axios.get<IO2oDriver>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`)
});

export const getEntities: ICrudGetAllAction<IO2oDriver> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_O2ODRIVER_LIST,
    payload: axios.get<IO2oDriver>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IO2oDriver> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_O2ODRIVER,
    payload: axios.get<IO2oDriver>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IO2oDriver> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_O2ODRIVER,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const updateEntity: ICrudPutAction<IO2oDriver> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_O2ODRIVER,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IO2oDriver> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_O2ODRIVER,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
