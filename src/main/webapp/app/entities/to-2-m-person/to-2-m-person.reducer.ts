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

import { ITo2mPerson, defaultValue } from 'app/shared/model/to-2-m-person.model';

export const ACTION_TYPES = {
  SEARCH_TO2MPEOPLE: 'to2mPerson/SEARCH_TO2MPEOPLE',
  FETCH_TO2MPERSON_LIST: 'to2mPerson/FETCH_TO2MPERSON_LIST',
  FETCH_TO2MPERSON: 'to2mPerson/FETCH_TO2MPERSON',
  CREATE_TO2MPERSON: 'to2mPerson/CREATE_TO2MPERSON',
  UPDATE_TO2MPERSON: 'to2mPerson/UPDATE_TO2MPERSON',
  DELETE_TO2MPERSON: 'to2mPerson/DELETE_TO2MPERSON',
  RESET: 'to2mPerson/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ITo2mPerson>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type To2mPersonState = Readonly<typeof initialState>;

// Reducer

export default (state: To2mPersonState = initialState, action): To2mPersonState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_TO2MPEOPLE):
    case REQUEST(ACTION_TYPES.FETCH_TO2MPERSON_LIST):
    case REQUEST(ACTION_TYPES.FETCH_TO2MPERSON):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_TO2MPERSON):
    case REQUEST(ACTION_TYPES.UPDATE_TO2MPERSON):
    case REQUEST(ACTION_TYPES.DELETE_TO2MPERSON):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_TO2MPEOPLE):
    case FAILURE(ACTION_TYPES.FETCH_TO2MPERSON_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TO2MPERSON):
    case FAILURE(ACTION_TYPES.CREATE_TO2MPERSON):
    case FAILURE(ACTION_TYPES.UPDATE_TO2MPERSON):
    case FAILURE(ACTION_TYPES.DELETE_TO2MPERSON):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_TO2MPEOPLE):
    case SUCCESS(ACTION_TYPES.FETCH_TO2MPERSON_LIST): {
      const links = parseHeaderForLinks(action.payload.headers.link);

      return {
        ...state,
        loading: false,
        links,
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links),
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    }
    case SUCCESS(ACTION_TYPES.FETCH_TO2MPERSON):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_TO2MPERSON):
    case SUCCESS(ACTION_TYPES.UPDATE_TO2MPERSON):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_TO2MPERSON):
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

const apiUrl = 'api/to-2-m-people';
const apiSearchUrl = 'api/_search/to-2-m-people';

// Actions

export const getSearchEntities: ICrudSearchAction<ITo2mPerson> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_TO2MPEOPLE,
  payload: axios.get<ITo2mPerson>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`)
});

export const getEntities: ICrudGetAllAction<ITo2mPerson> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_TO2MPERSON_LIST,
    payload: axios.get<ITo2mPerson>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<ITo2mPerson> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TO2MPERSON,
    payload: axios.get<ITo2mPerson>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ITo2mPerson> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TO2MPERSON,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const updateEntity: ICrudPutAction<ITo2mPerson> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_TO2MPERSON,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ITo2mPerson> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_TO2MPERSON,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
