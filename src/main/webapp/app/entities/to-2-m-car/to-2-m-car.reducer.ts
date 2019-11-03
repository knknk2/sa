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

import { ITo2mCar, defaultValue } from 'app/shared/model/to-2-m-car.model';

export const ACTION_TYPES = {
  SEARCH_TO2MCARS: 'to2mCar/SEARCH_TO2MCARS',
  FETCH_TO2MCAR_LIST: 'to2mCar/FETCH_TO2MCAR_LIST',
  FETCH_TO2MCAR: 'to2mCar/FETCH_TO2MCAR',
  CREATE_TO2MCAR: 'to2mCar/CREATE_TO2MCAR',
  UPDATE_TO2MCAR: 'to2mCar/UPDATE_TO2MCAR',
  DELETE_TO2MCAR: 'to2mCar/DELETE_TO2MCAR',
  RESET: 'to2mCar/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ITo2mCar>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type To2mCarState = Readonly<typeof initialState>;

// Reducer

export default (state: To2mCarState = initialState, action): To2mCarState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_TO2MCARS):
    case REQUEST(ACTION_TYPES.FETCH_TO2MCAR_LIST):
    case REQUEST(ACTION_TYPES.FETCH_TO2MCAR):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_TO2MCAR):
    case REQUEST(ACTION_TYPES.UPDATE_TO2MCAR):
    case REQUEST(ACTION_TYPES.DELETE_TO2MCAR):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_TO2MCARS):
    case FAILURE(ACTION_TYPES.FETCH_TO2MCAR_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TO2MCAR):
    case FAILURE(ACTION_TYPES.CREATE_TO2MCAR):
    case FAILURE(ACTION_TYPES.UPDATE_TO2MCAR):
    case FAILURE(ACTION_TYPES.DELETE_TO2MCAR):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_TO2MCARS):
    case SUCCESS(ACTION_TYPES.FETCH_TO2MCAR_LIST): {
      const links = parseHeaderForLinks(action.payload.headers.link);

      return {
        ...state,
        loading: false,
        links,
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links),
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    }
    case SUCCESS(ACTION_TYPES.FETCH_TO2MCAR):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_TO2MCAR):
    case SUCCESS(ACTION_TYPES.UPDATE_TO2MCAR):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_TO2MCAR):
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

const apiUrl = 'api/to-2-m-cars';
const apiSearchUrl = 'api/_search/to-2-m-cars';

// Actions

export const getSearchEntities: ICrudSearchAction<ITo2mCar> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_TO2MCARS,
  payload: axios.get<ITo2mCar>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`)
});

export const getEntities: ICrudGetAllAction<ITo2mCar> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_TO2MCAR_LIST,
    payload: axios.get<ITo2mCar>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<ITo2mCar> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TO2MCAR,
    payload: axios.get<ITo2mCar>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ITo2mCar> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TO2MCAR,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const updateEntity: ICrudPutAction<ITo2mCar> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_TO2MCAR,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ITo2mCar> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_TO2MCAR,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
