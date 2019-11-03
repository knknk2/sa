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

import { IBo2mOwnerDTO, defaultValue } from 'app/shared/model/bo-2-m-owner-dto.model';

export const ACTION_TYPES = {
  SEARCH_BO2MOWNERDTOS: 'bo2mOwnerDTO/SEARCH_BO2MOWNERDTOS',
  FETCH_BO2MOWNERDTO_LIST: 'bo2mOwnerDTO/FETCH_BO2MOWNERDTO_LIST',
  FETCH_BO2MOWNERDTO: 'bo2mOwnerDTO/FETCH_BO2MOWNERDTO',
  CREATE_BO2MOWNERDTO: 'bo2mOwnerDTO/CREATE_BO2MOWNERDTO',
  UPDATE_BO2MOWNERDTO: 'bo2mOwnerDTO/UPDATE_BO2MOWNERDTO',
  DELETE_BO2MOWNERDTO: 'bo2mOwnerDTO/DELETE_BO2MOWNERDTO',
  RESET: 'bo2mOwnerDTO/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IBo2mOwnerDTO>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type Bo2mOwnerDTOState = Readonly<typeof initialState>;

// Reducer

export default (state: Bo2mOwnerDTOState = initialState, action): Bo2mOwnerDTOState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_BO2MOWNERDTOS):
    case REQUEST(ACTION_TYPES.FETCH_BO2MOWNERDTO_LIST):
    case REQUEST(ACTION_TYPES.FETCH_BO2MOWNERDTO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_BO2MOWNERDTO):
    case REQUEST(ACTION_TYPES.UPDATE_BO2MOWNERDTO):
    case REQUEST(ACTION_TYPES.DELETE_BO2MOWNERDTO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_BO2MOWNERDTOS):
    case FAILURE(ACTION_TYPES.FETCH_BO2MOWNERDTO_LIST):
    case FAILURE(ACTION_TYPES.FETCH_BO2MOWNERDTO):
    case FAILURE(ACTION_TYPES.CREATE_BO2MOWNERDTO):
    case FAILURE(ACTION_TYPES.UPDATE_BO2MOWNERDTO):
    case FAILURE(ACTION_TYPES.DELETE_BO2MOWNERDTO):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_BO2MOWNERDTOS):
    case SUCCESS(ACTION_TYPES.FETCH_BO2MOWNERDTO_LIST): {
      const links = parseHeaderForLinks(action.payload.headers.link);

      return {
        ...state,
        loading: false,
        links,
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links),
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    }
    case SUCCESS(ACTION_TYPES.FETCH_BO2MOWNERDTO):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_BO2MOWNERDTO):
    case SUCCESS(ACTION_TYPES.UPDATE_BO2MOWNERDTO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_BO2MOWNERDTO):
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

const apiUrl = 'api/bo-2-m-owner-dtos';
const apiSearchUrl = 'api/_search/bo-2-m-owner-dtos';

// Actions

export const getSearchEntities: ICrudSearchAction<IBo2mOwnerDTO> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_BO2MOWNERDTOS,
  payload: axios.get<IBo2mOwnerDTO>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`)
});

export const getEntities: ICrudGetAllAction<IBo2mOwnerDTO> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_BO2MOWNERDTO_LIST,
    payload: axios.get<IBo2mOwnerDTO>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IBo2mOwnerDTO> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_BO2MOWNERDTO,
    payload: axios.get<IBo2mOwnerDTO>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IBo2mOwnerDTO> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_BO2MOWNERDTO,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const updateEntity: ICrudPutAction<IBo2mOwnerDTO> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_BO2MOWNERDTO,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IBo2mOwnerDTO> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_BO2MOWNERDTO,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
