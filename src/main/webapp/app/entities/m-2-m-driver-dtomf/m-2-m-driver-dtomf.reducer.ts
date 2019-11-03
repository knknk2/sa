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

import { IM2mDriverDTOMF, defaultValue } from 'app/shared/model/m-2-m-driver-dtomf.model';

export const ACTION_TYPES = {
  SEARCH_M2MDRIVERDTOMFS: 'm2mDriverDTOMF/SEARCH_M2MDRIVERDTOMFS',
  FETCH_M2MDRIVERDTOMF_LIST: 'm2mDriverDTOMF/FETCH_M2MDRIVERDTOMF_LIST',
  FETCH_M2MDRIVERDTOMF: 'm2mDriverDTOMF/FETCH_M2MDRIVERDTOMF',
  CREATE_M2MDRIVERDTOMF: 'm2mDriverDTOMF/CREATE_M2MDRIVERDTOMF',
  UPDATE_M2MDRIVERDTOMF: 'm2mDriverDTOMF/UPDATE_M2MDRIVERDTOMF',
  DELETE_M2MDRIVERDTOMF: 'm2mDriverDTOMF/DELETE_M2MDRIVERDTOMF',
  RESET: 'm2mDriverDTOMF/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IM2mDriverDTOMF>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type M2mDriverDTOMFState = Readonly<typeof initialState>;

// Reducer

export default (state: M2mDriverDTOMFState = initialState, action): M2mDriverDTOMFState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_M2MDRIVERDTOMFS):
    case REQUEST(ACTION_TYPES.FETCH_M2MDRIVERDTOMF_LIST):
    case REQUEST(ACTION_TYPES.FETCH_M2MDRIVERDTOMF):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_M2MDRIVERDTOMF):
    case REQUEST(ACTION_TYPES.UPDATE_M2MDRIVERDTOMF):
    case REQUEST(ACTION_TYPES.DELETE_M2MDRIVERDTOMF):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_M2MDRIVERDTOMFS):
    case FAILURE(ACTION_TYPES.FETCH_M2MDRIVERDTOMF_LIST):
    case FAILURE(ACTION_TYPES.FETCH_M2MDRIVERDTOMF):
    case FAILURE(ACTION_TYPES.CREATE_M2MDRIVERDTOMF):
    case FAILURE(ACTION_TYPES.UPDATE_M2MDRIVERDTOMF):
    case FAILURE(ACTION_TYPES.DELETE_M2MDRIVERDTOMF):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_M2MDRIVERDTOMFS):
    case SUCCESS(ACTION_TYPES.FETCH_M2MDRIVERDTOMF_LIST): {
      const links = parseHeaderForLinks(action.payload.headers.link);

      return {
        ...state,
        loading: false,
        links,
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links),
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    }
    case SUCCESS(ACTION_TYPES.FETCH_M2MDRIVERDTOMF):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_M2MDRIVERDTOMF):
    case SUCCESS(ACTION_TYPES.UPDATE_M2MDRIVERDTOMF):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_M2MDRIVERDTOMF):
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

const apiUrl = 'api/m-2-m-driver-dtomfs';
const apiSearchUrl = 'api/_search/m-2-m-driver-dtomfs';

// Actions

export const getSearchEntities: ICrudSearchAction<IM2mDriverDTOMF> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_M2MDRIVERDTOMFS,
  payload: axios.get<IM2mDriverDTOMF>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`)
});

export const getEntities: ICrudGetAllAction<IM2mDriverDTOMF> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_M2MDRIVERDTOMF_LIST,
    payload: axios.get<IM2mDriverDTOMF>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IM2mDriverDTOMF> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_M2MDRIVERDTOMF,
    payload: axios.get<IM2mDriverDTOMF>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IM2mDriverDTOMF> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_M2MDRIVERDTOMF,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const updateEntity: ICrudPutAction<IM2mDriverDTOMF> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_M2MDRIVERDTOMF,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IM2mDriverDTOMF> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_M2MDRIVERDTOMF,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
