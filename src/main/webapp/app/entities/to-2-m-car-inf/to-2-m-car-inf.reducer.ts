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

import { ITo2mCarInf, defaultValue } from 'app/shared/model/to-2-m-car-inf.model';

export const ACTION_TYPES = {
  SEARCH_TO2MCARINFS: 'to2mCarInf/SEARCH_TO2MCARINFS',
  FETCH_TO2MCARINF_LIST: 'to2mCarInf/FETCH_TO2MCARINF_LIST',
  FETCH_TO2MCARINF: 'to2mCarInf/FETCH_TO2MCARINF',
  CREATE_TO2MCARINF: 'to2mCarInf/CREATE_TO2MCARINF',
  UPDATE_TO2MCARINF: 'to2mCarInf/UPDATE_TO2MCARINF',
  DELETE_TO2MCARINF: 'to2mCarInf/DELETE_TO2MCARINF',
  RESET: 'to2mCarInf/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ITo2mCarInf>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type To2mCarInfState = Readonly<typeof initialState>;

// Reducer

export default (state: To2mCarInfState = initialState, action): To2mCarInfState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_TO2MCARINFS):
    case REQUEST(ACTION_TYPES.FETCH_TO2MCARINF_LIST):
    case REQUEST(ACTION_TYPES.FETCH_TO2MCARINF):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_TO2MCARINF):
    case REQUEST(ACTION_TYPES.UPDATE_TO2MCARINF):
    case REQUEST(ACTION_TYPES.DELETE_TO2MCARINF):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_TO2MCARINFS):
    case FAILURE(ACTION_TYPES.FETCH_TO2MCARINF_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TO2MCARINF):
    case FAILURE(ACTION_TYPES.CREATE_TO2MCARINF):
    case FAILURE(ACTION_TYPES.UPDATE_TO2MCARINF):
    case FAILURE(ACTION_TYPES.DELETE_TO2MCARINF):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_TO2MCARINFS):
    case SUCCESS(ACTION_TYPES.FETCH_TO2MCARINF_LIST): {
      const links = parseHeaderForLinks(action.payload.headers.link);

      return {
        ...state,
        loading: false,
        links,
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links),
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    }
    case SUCCESS(ACTION_TYPES.FETCH_TO2MCARINF):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_TO2MCARINF):
    case SUCCESS(ACTION_TYPES.UPDATE_TO2MCARINF):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_TO2MCARINF):
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

const apiUrl = 'api/to-2-m-car-infs';
const apiSearchUrl = 'api/_search/to-2-m-car-infs';

// Actions

export const getSearchEntities: ICrudSearchAction<ITo2mCarInf> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_TO2MCARINFS,
  payload: axios.get<ITo2mCarInf>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`)
});

export const getEntities: ICrudGetAllAction<ITo2mCarInf> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_TO2MCARINF_LIST,
    payload: axios.get<ITo2mCarInf>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<ITo2mCarInf> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TO2MCARINF,
    payload: axios.get<ITo2mCarInf>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ITo2mCarInf> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TO2MCARINF,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const updateEntity: ICrudPutAction<ITo2mCarInf> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_TO2MCARINF,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ITo2mCarInf> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_TO2MCARINF,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
