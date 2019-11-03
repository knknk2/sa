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

import { IM2mCarDTOMF, defaultValue } from 'app/shared/model/m-2-m-car-dtomf.model';

export const ACTION_TYPES = {
  SEARCH_M2MCARDTOMFS: 'm2mCarDTOMF/SEARCH_M2MCARDTOMFS',
  FETCH_M2MCARDTOMF_LIST: 'm2mCarDTOMF/FETCH_M2MCARDTOMF_LIST',
  FETCH_M2MCARDTOMF: 'm2mCarDTOMF/FETCH_M2MCARDTOMF',
  CREATE_M2MCARDTOMF: 'm2mCarDTOMF/CREATE_M2MCARDTOMF',
  UPDATE_M2MCARDTOMF: 'm2mCarDTOMF/UPDATE_M2MCARDTOMF',
  DELETE_M2MCARDTOMF: 'm2mCarDTOMF/DELETE_M2MCARDTOMF',
  RESET: 'm2mCarDTOMF/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IM2mCarDTOMF>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type M2mCarDTOMFState = Readonly<typeof initialState>;

// Reducer

export default (state: M2mCarDTOMFState = initialState, action): M2mCarDTOMFState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_M2MCARDTOMFS):
    case REQUEST(ACTION_TYPES.FETCH_M2MCARDTOMF_LIST):
    case REQUEST(ACTION_TYPES.FETCH_M2MCARDTOMF):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_M2MCARDTOMF):
    case REQUEST(ACTION_TYPES.UPDATE_M2MCARDTOMF):
    case REQUEST(ACTION_TYPES.DELETE_M2MCARDTOMF):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_M2MCARDTOMFS):
    case FAILURE(ACTION_TYPES.FETCH_M2MCARDTOMF_LIST):
    case FAILURE(ACTION_TYPES.FETCH_M2MCARDTOMF):
    case FAILURE(ACTION_TYPES.CREATE_M2MCARDTOMF):
    case FAILURE(ACTION_TYPES.UPDATE_M2MCARDTOMF):
    case FAILURE(ACTION_TYPES.DELETE_M2MCARDTOMF):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_M2MCARDTOMFS):
    case SUCCESS(ACTION_TYPES.FETCH_M2MCARDTOMF_LIST): {
      const links = parseHeaderForLinks(action.payload.headers.link);

      return {
        ...state,
        loading: false,
        links,
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links),
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    }
    case SUCCESS(ACTION_TYPES.FETCH_M2MCARDTOMF):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_M2MCARDTOMF):
    case SUCCESS(ACTION_TYPES.UPDATE_M2MCARDTOMF):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_M2MCARDTOMF):
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

const apiUrl = 'api/m-2-m-car-dtomfs';
const apiSearchUrl = 'api/_search/m-2-m-car-dtomfs';

// Actions

export const getSearchEntities: ICrudSearchAction<IM2mCarDTOMF> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_M2MCARDTOMFS,
  payload: axios.get<IM2mCarDTOMF>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`)
});

export const getEntities: ICrudGetAllAction<IM2mCarDTOMF> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_M2MCARDTOMF_LIST,
    payload: axios.get<IM2mCarDTOMF>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IM2mCarDTOMF> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_M2MCARDTOMF,
    payload: axios.get<IM2mCarDTOMF>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IM2mCarDTOMF> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_M2MCARDTOMF,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const updateEntity: ICrudPutAction<IM2mCarDTOMF> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_M2MCARDTOMF,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IM2mCarDTOMF> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_M2MCARDTOMF,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
