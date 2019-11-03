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

import { IBo2mOwner, defaultValue } from 'app/shared/model/bo-2-m-owner.model';

export const ACTION_TYPES = {
  SEARCH_BO2MOWNERS: 'bo2mOwner/SEARCH_BO2MOWNERS',
  FETCH_BO2MOWNER_LIST: 'bo2mOwner/FETCH_BO2MOWNER_LIST',
  FETCH_BO2MOWNER: 'bo2mOwner/FETCH_BO2MOWNER',
  CREATE_BO2MOWNER: 'bo2mOwner/CREATE_BO2MOWNER',
  UPDATE_BO2MOWNER: 'bo2mOwner/UPDATE_BO2MOWNER',
  DELETE_BO2MOWNER: 'bo2mOwner/DELETE_BO2MOWNER',
  RESET: 'bo2mOwner/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IBo2mOwner>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type Bo2mOwnerState = Readonly<typeof initialState>;

// Reducer

export default (state: Bo2mOwnerState = initialState, action): Bo2mOwnerState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_BO2MOWNERS):
    case REQUEST(ACTION_TYPES.FETCH_BO2MOWNER_LIST):
    case REQUEST(ACTION_TYPES.FETCH_BO2MOWNER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_BO2MOWNER):
    case REQUEST(ACTION_TYPES.UPDATE_BO2MOWNER):
    case REQUEST(ACTION_TYPES.DELETE_BO2MOWNER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_BO2MOWNERS):
    case FAILURE(ACTION_TYPES.FETCH_BO2MOWNER_LIST):
    case FAILURE(ACTION_TYPES.FETCH_BO2MOWNER):
    case FAILURE(ACTION_TYPES.CREATE_BO2MOWNER):
    case FAILURE(ACTION_TYPES.UPDATE_BO2MOWNER):
    case FAILURE(ACTION_TYPES.DELETE_BO2MOWNER):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_BO2MOWNERS):
    case SUCCESS(ACTION_TYPES.FETCH_BO2MOWNER_LIST): {
      const links = parseHeaderForLinks(action.payload.headers.link);

      return {
        ...state,
        loading: false,
        links,
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links),
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    }
    case SUCCESS(ACTION_TYPES.FETCH_BO2MOWNER):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_BO2MOWNER):
    case SUCCESS(ACTION_TYPES.UPDATE_BO2MOWNER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_BO2MOWNER):
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

const apiUrl = 'api/bo-2-m-owners';
const apiSearchUrl = 'api/_search/bo-2-m-owners';

// Actions

export const getSearchEntities: ICrudSearchAction<IBo2mOwner> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_BO2MOWNERS,
  payload: axios.get<IBo2mOwner>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`)
});

export const getEntities: ICrudGetAllAction<IBo2mOwner> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_BO2MOWNER_LIST,
    payload: axios.get<IBo2mOwner>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IBo2mOwner> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_BO2MOWNER,
    payload: axios.get<IBo2mOwner>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IBo2mOwner> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_BO2MOWNER,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const updateEntity: ICrudPutAction<IBo2mOwner> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_BO2MOWNER,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IBo2mOwner> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_BO2MOWNER,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
