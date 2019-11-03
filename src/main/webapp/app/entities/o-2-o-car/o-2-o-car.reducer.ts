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

import { IO2oCar, defaultValue } from 'app/shared/model/o-2-o-car.model';

export const ACTION_TYPES = {
  SEARCH_O2OCARS: 'o2oCar/SEARCH_O2OCARS',
  FETCH_O2OCAR_LIST: 'o2oCar/FETCH_O2OCAR_LIST',
  FETCH_O2OCAR: 'o2oCar/FETCH_O2OCAR',
  CREATE_O2OCAR: 'o2oCar/CREATE_O2OCAR',
  UPDATE_O2OCAR: 'o2oCar/UPDATE_O2OCAR',
  DELETE_O2OCAR: 'o2oCar/DELETE_O2OCAR',
  RESET: 'o2oCar/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IO2oCar>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type O2oCarState = Readonly<typeof initialState>;

// Reducer

export default (state: O2oCarState = initialState, action): O2oCarState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_O2OCARS):
    case REQUEST(ACTION_TYPES.FETCH_O2OCAR_LIST):
    case REQUEST(ACTION_TYPES.FETCH_O2OCAR):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_O2OCAR):
    case REQUEST(ACTION_TYPES.UPDATE_O2OCAR):
    case REQUEST(ACTION_TYPES.DELETE_O2OCAR):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_O2OCARS):
    case FAILURE(ACTION_TYPES.FETCH_O2OCAR_LIST):
    case FAILURE(ACTION_TYPES.FETCH_O2OCAR):
    case FAILURE(ACTION_TYPES.CREATE_O2OCAR):
    case FAILURE(ACTION_TYPES.UPDATE_O2OCAR):
    case FAILURE(ACTION_TYPES.DELETE_O2OCAR):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_O2OCARS):
    case SUCCESS(ACTION_TYPES.FETCH_O2OCAR_LIST): {
      const links = parseHeaderForLinks(action.payload.headers.link);

      return {
        ...state,
        loading: false,
        links,
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links),
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    }
    case SUCCESS(ACTION_TYPES.FETCH_O2OCAR):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_O2OCAR):
    case SUCCESS(ACTION_TYPES.UPDATE_O2OCAR):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_O2OCAR):
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

const apiUrl = 'api/o-2-o-cars';
const apiSearchUrl = 'api/_search/o-2-o-cars';

// Actions

export const getSearchEntities: ICrudSearchAction<IO2oCar> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_O2OCARS,
  payload: axios.get<IO2oCar>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`)
});

export const getEntities: ICrudGetAllAction<IO2oCar> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_O2OCAR_LIST,
    payload: axios.get<IO2oCar>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IO2oCar> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_O2OCAR,
    payload: axios.get<IO2oCar>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IO2oCar> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_O2OCAR,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const updateEntity: ICrudPutAction<IO2oCar> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_O2OCAR,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IO2oCar> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_O2OCAR,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
