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

import { ITo2mPersonInf, defaultValue } from 'app/shared/model/to-2-m-person-inf.model';

export const ACTION_TYPES = {
  SEARCH_TO2MPERSONINFS: 'to2mPersonInf/SEARCH_TO2MPERSONINFS',
  FETCH_TO2MPERSONINF_LIST: 'to2mPersonInf/FETCH_TO2MPERSONINF_LIST',
  FETCH_TO2MPERSONINF: 'to2mPersonInf/FETCH_TO2MPERSONINF',
  CREATE_TO2MPERSONINF: 'to2mPersonInf/CREATE_TO2MPERSONINF',
  UPDATE_TO2MPERSONINF: 'to2mPersonInf/UPDATE_TO2MPERSONINF',
  DELETE_TO2MPERSONINF: 'to2mPersonInf/DELETE_TO2MPERSONINF',
  RESET: 'to2mPersonInf/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ITo2mPersonInf>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type To2mPersonInfState = Readonly<typeof initialState>;

// Reducer

export default (state: To2mPersonInfState = initialState, action): To2mPersonInfState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_TO2MPERSONINFS):
    case REQUEST(ACTION_TYPES.FETCH_TO2MPERSONINF_LIST):
    case REQUEST(ACTION_TYPES.FETCH_TO2MPERSONINF):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_TO2MPERSONINF):
    case REQUEST(ACTION_TYPES.UPDATE_TO2MPERSONINF):
    case REQUEST(ACTION_TYPES.DELETE_TO2MPERSONINF):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_TO2MPERSONINFS):
    case FAILURE(ACTION_TYPES.FETCH_TO2MPERSONINF_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TO2MPERSONINF):
    case FAILURE(ACTION_TYPES.CREATE_TO2MPERSONINF):
    case FAILURE(ACTION_TYPES.UPDATE_TO2MPERSONINF):
    case FAILURE(ACTION_TYPES.DELETE_TO2MPERSONINF):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_TO2MPERSONINFS):
    case SUCCESS(ACTION_TYPES.FETCH_TO2MPERSONINF_LIST): {
      const links = parseHeaderForLinks(action.payload.headers.link);

      return {
        ...state,
        loading: false,
        links,
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links),
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    }
    case SUCCESS(ACTION_TYPES.FETCH_TO2MPERSONINF):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_TO2MPERSONINF):
    case SUCCESS(ACTION_TYPES.UPDATE_TO2MPERSONINF):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_TO2MPERSONINF):
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

const apiUrl = 'api/to-2-m-person-infs';
const apiSearchUrl = 'api/_search/to-2-m-person-infs';

// Actions

export const getSearchEntities: ICrudSearchAction<ITo2mPersonInf> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_TO2MPERSONINFS,
  payload: axios.get<ITo2mPersonInf>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`)
});

export const getEntities: ICrudGetAllAction<ITo2mPersonInf> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_TO2MPERSONINF_LIST,
    payload: axios.get<ITo2mPersonInf>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<ITo2mPersonInf> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TO2MPERSONINF,
    payload: axios.get<ITo2mPersonInf>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ITo2mPersonInf> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TO2MPERSONINF,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const updateEntity: ICrudPutAction<ITo2mPersonInf> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_TO2MPERSONINF,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ITo2mPersonInf> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_TO2MPERSONINF,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
