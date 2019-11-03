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

import { IM2mCar, defaultValue } from 'app/shared/model/m-2-m-car.model';

export const ACTION_TYPES = {
  SEARCH_M2MCARS: 'm2mCar/SEARCH_M2MCARS',
  FETCH_M2MCAR_LIST: 'm2mCar/FETCH_M2MCAR_LIST',
  FETCH_M2MCAR: 'm2mCar/FETCH_M2MCAR',
  CREATE_M2MCAR: 'm2mCar/CREATE_M2MCAR',
  UPDATE_M2MCAR: 'm2mCar/UPDATE_M2MCAR',
  DELETE_M2MCAR: 'm2mCar/DELETE_M2MCAR',
  RESET: 'm2mCar/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IM2mCar>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type M2mCarState = Readonly<typeof initialState>;

// Reducer

export default (state: M2mCarState = initialState, action): M2mCarState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_M2MCARS):
    case REQUEST(ACTION_TYPES.FETCH_M2MCAR_LIST):
    case REQUEST(ACTION_TYPES.FETCH_M2MCAR):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_M2MCAR):
    case REQUEST(ACTION_TYPES.UPDATE_M2MCAR):
    case REQUEST(ACTION_TYPES.DELETE_M2MCAR):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_M2MCARS):
    case FAILURE(ACTION_TYPES.FETCH_M2MCAR_LIST):
    case FAILURE(ACTION_TYPES.FETCH_M2MCAR):
    case FAILURE(ACTION_TYPES.CREATE_M2MCAR):
    case FAILURE(ACTION_TYPES.UPDATE_M2MCAR):
    case FAILURE(ACTION_TYPES.DELETE_M2MCAR):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_M2MCARS):
    case SUCCESS(ACTION_TYPES.FETCH_M2MCAR_LIST): {
      const links = parseHeaderForLinks(action.payload.headers.link);

      return {
        ...state,
        loading: false,
        links,
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links),
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    }
    case SUCCESS(ACTION_TYPES.FETCH_M2MCAR):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_M2MCAR):
    case SUCCESS(ACTION_TYPES.UPDATE_M2MCAR):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_M2MCAR):
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

const apiUrl = 'api/m-2-m-cars';
const apiSearchUrl = 'api/_search/m-2-m-cars';

// Actions

export const getSearchEntities: ICrudSearchAction<IM2mCar> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_M2MCARS,
  payload: axios.get<IM2mCar>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`)
});

export const getEntities: ICrudGetAllAction<IM2mCar> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_M2MCAR_LIST,
    payload: axios.get<IM2mCar>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IM2mCar> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_M2MCAR,
    payload: axios.get<IM2mCar>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IM2mCar> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_M2MCAR,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const updateEntity: ICrudPutAction<IM2mCar> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_M2MCAR,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IM2mCar> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_M2MCAR,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
