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

import { IBo2mCar, defaultValue } from 'app/shared/model/bo-2-m-car.model';

export const ACTION_TYPES = {
  SEARCH_BO2MCARS: 'bo2mCar/SEARCH_BO2MCARS',
  FETCH_BO2MCAR_LIST: 'bo2mCar/FETCH_BO2MCAR_LIST',
  FETCH_BO2MCAR: 'bo2mCar/FETCH_BO2MCAR',
  CREATE_BO2MCAR: 'bo2mCar/CREATE_BO2MCAR',
  UPDATE_BO2MCAR: 'bo2mCar/UPDATE_BO2MCAR',
  DELETE_BO2MCAR: 'bo2mCar/DELETE_BO2MCAR',
  RESET: 'bo2mCar/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IBo2mCar>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type Bo2mCarState = Readonly<typeof initialState>;

// Reducer

export default (state: Bo2mCarState = initialState, action): Bo2mCarState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_BO2MCARS):
    case REQUEST(ACTION_TYPES.FETCH_BO2MCAR_LIST):
    case REQUEST(ACTION_TYPES.FETCH_BO2MCAR):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_BO2MCAR):
    case REQUEST(ACTION_TYPES.UPDATE_BO2MCAR):
    case REQUEST(ACTION_TYPES.DELETE_BO2MCAR):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_BO2MCARS):
    case FAILURE(ACTION_TYPES.FETCH_BO2MCAR_LIST):
    case FAILURE(ACTION_TYPES.FETCH_BO2MCAR):
    case FAILURE(ACTION_TYPES.CREATE_BO2MCAR):
    case FAILURE(ACTION_TYPES.UPDATE_BO2MCAR):
    case FAILURE(ACTION_TYPES.DELETE_BO2MCAR):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_BO2MCARS):
    case SUCCESS(ACTION_TYPES.FETCH_BO2MCAR_LIST): {
      const links = parseHeaderForLinks(action.payload.headers.link);

      return {
        ...state,
        loading: false,
        links,
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links),
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    }
    case SUCCESS(ACTION_TYPES.FETCH_BO2MCAR):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_BO2MCAR):
    case SUCCESS(ACTION_TYPES.UPDATE_BO2MCAR):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_BO2MCAR):
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

const apiUrl = 'api/bo-2-m-cars';
const apiSearchUrl = 'api/_search/bo-2-m-cars';

// Actions

export const getSearchEntities: ICrudSearchAction<IBo2mCar> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_BO2MCARS,
  payload: axios.get<IBo2mCar>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`)
});

export const getEntities: ICrudGetAllAction<IBo2mCar> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_BO2MCAR_LIST,
    payload: axios.get<IBo2mCar>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IBo2mCar> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_BO2MCAR,
    payload: axios.get<IBo2mCar>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IBo2mCar> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_BO2MCAR,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const updateEntity: ICrudPutAction<IBo2mCar> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_BO2MCAR,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IBo2mCar> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_BO2MCAR,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
