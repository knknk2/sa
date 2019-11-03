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

import { IUo2oCitizen, defaultValue } from 'app/shared/model/uo-2-o-citizen.model';

export const ACTION_TYPES = {
  SEARCH_UO2OCITIZENS: 'uo2oCitizen/SEARCH_UO2OCITIZENS',
  FETCH_UO2OCITIZEN_LIST: 'uo2oCitizen/FETCH_UO2OCITIZEN_LIST',
  FETCH_UO2OCITIZEN: 'uo2oCitizen/FETCH_UO2OCITIZEN',
  CREATE_UO2OCITIZEN: 'uo2oCitizen/CREATE_UO2OCITIZEN',
  UPDATE_UO2OCITIZEN: 'uo2oCitizen/UPDATE_UO2OCITIZEN',
  DELETE_UO2OCITIZEN: 'uo2oCitizen/DELETE_UO2OCITIZEN',
  RESET: 'uo2oCitizen/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IUo2oCitizen>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type Uo2oCitizenState = Readonly<typeof initialState>;

// Reducer

export default (state: Uo2oCitizenState = initialState, action): Uo2oCitizenState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_UO2OCITIZENS):
    case REQUEST(ACTION_TYPES.FETCH_UO2OCITIZEN_LIST):
    case REQUEST(ACTION_TYPES.FETCH_UO2OCITIZEN):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_UO2OCITIZEN):
    case REQUEST(ACTION_TYPES.UPDATE_UO2OCITIZEN):
    case REQUEST(ACTION_TYPES.DELETE_UO2OCITIZEN):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_UO2OCITIZENS):
    case FAILURE(ACTION_TYPES.FETCH_UO2OCITIZEN_LIST):
    case FAILURE(ACTION_TYPES.FETCH_UO2OCITIZEN):
    case FAILURE(ACTION_TYPES.CREATE_UO2OCITIZEN):
    case FAILURE(ACTION_TYPES.UPDATE_UO2OCITIZEN):
    case FAILURE(ACTION_TYPES.DELETE_UO2OCITIZEN):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_UO2OCITIZENS):
    case SUCCESS(ACTION_TYPES.FETCH_UO2OCITIZEN_LIST): {
      const links = parseHeaderForLinks(action.payload.headers.link);

      return {
        ...state,
        loading: false,
        links,
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links),
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    }
    case SUCCESS(ACTION_TYPES.FETCH_UO2OCITIZEN):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_UO2OCITIZEN):
    case SUCCESS(ACTION_TYPES.UPDATE_UO2OCITIZEN):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_UO2OCITIZEN):
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

const apiUrl = 'api/uo-2-o-citizens';
const apiSearchUrl = 'api/_search/uo-2-o-citizens';

// Actions

export const getSearchEntities: ICrudSearchAction<IUo2oCitizen> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_UO2OCITIZENS,
  payload: axios.get<IUo2oCitizen>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`)
});

export const getEntities: ICrudGetAllAction<IUo2oCitizen> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_UO2OCITIZEN_LIST,
    payload: axios.get<IUo2oCitizen>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IUo2oCitizen> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_UO2OCITIZEN,
    payload: axios.get<IUo2oCitizen>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IUo2oCitizen> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_UO2OCITIZEN,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const updateEntity: ICrudPutAction<IUo2oCitizen> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_UO2OCITIZEN,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IUo2oCitizen> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_UO2OCITIZEN,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
