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

import { IBo2mCarDTO, defaultValue } from 'app/shared/model/bo-2-m-car-dto.model';

export const ACTION_TYPES = {
  SEARCH_BO2MCARDTOS: 'bo2mCarDTO/SEARCH_BO2MCARDTOS',
  FETCH_BO2MCARDTO_LIST: 'bo2mCarDTO/FETCH_BO2MCARDTO_LIST',
  FETCH_BO2MCARDTO: 'bo2mCarDTO/FETCH_BO2MCARDTO',
  CREATE_BO2MCARDTO: 'bo2mCarDTO/CREATE_BO2MCARDTO',
  UPDATE_BO2MCARDTO: 'bo2mCarDTO/UPDATE_BO2MCARDTO',
  DELETE_BO2MCARDTO: 'bo2mCarDTO/DELETE_BO2MCARDTO',
  RESET: 'bo2mCarDTO/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IBo2mCarDTO>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type Bo2mCarDTOState = Readonly<typeof initialState>;

// Reducer

export default (state: Bo2mCarDTOState = initialState, action): Bo2mCarDTOState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_BO2MCARDTOS):
    case REQUEST(ACTION_TYPES.FETCH_BO2MCARDTO_LIST):
    case REQUEST(ACTION_TYPES.FETCH_BO2MCARDTO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_BO2MCARDTO):
    case REQUEST(ACTION_TYPES.UPDATE_BO2MCARDTO):
    case REQUEST(ACTION_TYPES.DELETE_BO2MCARDTO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_BO2MCARDTOS):
    case FAILURE(ACTION_TYPES.FETCH_BO2MCARDTO_LIST):
    case FAILURE(ACTION_TYPES.FETCH_BO2MCARDTO):
    case FAILURE(ACTION_TYPES.CREATE_BO2MCARDTO):
    case FAILURE(ACTION_TYPES.UPDATE_BO2MCARDTO):
    case FAILURE(ACTION_TYPES.DELETE_BO2MCARDTO):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_BO2MCARDTOS):
    case SUCCESS(ACTION_TYPES.FETCH_BO2MCARDTO_LIST): {
      const links = parseHeaderForLinks(action.payload.headers.link);

      return {
        ...state,
        loading: false,
        links,
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links),
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    }
    case SUCCESS(ACTION_TYPES.FETCH_BO2MCARDTO):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_BO2MCARDTO):
    case SUCCESS(ACTION_TYPES.UPDATE_BO2MCARDTO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_BO2MCARDTO):
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

const apiUrl = 'api/bo-2-m-car-dtos';
const apiSearchUrl = 'api/_search/bo-2-m-car-dtos';

// Actions

export const getSearchEntities: ICrudSearchAction<IBo2mCarDTO> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_BO2MCARDTOS,
  payload: axios.get<IBo2mCarDTO>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`)
});

export const getEntities: ICrudGetAllAction<IBo2mCarDTO> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_BO2MCARDTO_LIST,
    payload: axios.get<IBo2mCarDTO>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IBo2mCarDTO> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_BO2MCARDTO,
    payload: axios.get<IBo2mCarDTO>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IBo2mCarDTO> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_BO2MCARDTO,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const updateEntity: ICrudPutAction<IBo2mCarDTO> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_BO2MCARDTO,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IBo2mCarDTO> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_BO2MCARDTO,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
