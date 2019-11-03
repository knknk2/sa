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

import { IUo2oPassport, defaultValue } from 'app/shared/model/uo-2-o-passport.model';

export const ACTION_TYPES = {
  SEARCH_UO2OPASSPORTS: 'uo2oPassport/SEARCH_UO2OPASSPORTS',
  FETCH_UO2OPASSPORT_LIST: 'uo2oPassport/FETCH_UO2OPASSPORT_LIST',
  FETCH_UO2OPASSPORT: 'uo2oPassport/FETCH_UO2OPASSPORT',
  CREATE_UO2OPASSPORT: 'uo2oPassport/CREATE_UO2OPASSPORT',
  UPDATE_UO2OPASSPORT: 'uo2oPassport/UPDATE_UO2OPASSPORT',
  DELETE_UO2OPASSPORT: 'uo2oPassport/DELETE_UO2OPASSPORT',
  RESET: 'uo2oPassport/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IUo2oPassport>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type Uo2oPassportState = Readonly<typeof initialState>;

// Reducer

export default (state: Uo2oPassportState = initialState, action): Uo2oPassportState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_UO2OPASSPORTS):
    case REQUEST(ACTION_TYPES.FETCH_UO2OPASSPORT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_UO2OPASSPORT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_UO2OPASSPORT):
    case REQUEST(ACTION_TYPES.UPDATE_UO2OPASSPORT):
    case REQUEST(ACTION_TYPES.DELETE_UO2OPASSPORT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_UO2OPASSPORTS):
    case FAILURE(ACTION_TYPES.FETCH_UO2OPASSPORT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_UO2OPASSPORT):
    case FAILURE(ACTION_TYPES.CREATE_UO2OPASSPORT):
    case FAILURE(ACTION_TYPES.UPDATE_UO2OPASSPORT):
    case FAILURE(ACTION_TYPES.DELETE_UO2OPASSPORT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_UO2OPASSPORTS):
    case SUCCESS(ACTION_TYPES.FETCH_UO2OPASSPORT_LIST): {
      const links = parseHeaderForLinks(action.payload.headers.link);

      return {
        ...state,
        loading: false,
        links,
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links),
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    }
    case SUCCESS(ACTION_TYPES.FETCH_UO2OPASSPORT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_UO2OPASSPORT):
    case SUCCESS(ACTION_TYPES.UPDATE_UO2OPASSPORT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_UO2OPASSPORT):
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

const apiUrl = 'api/uo-2-o-passports';
const apiSearchUrl = 'api/_search/uo-2-o-passports';

// Actions

export const getSearchEntities: ICrudSearchAction<IUo2oPassport> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_UO2OPASSPORTS,
  payload: axios.get<IUo2oPassport>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`)
});

export const getEntities: ICrudGetAllAction<IUo2oPassport> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_UO2OPASSPORT_LIST,
    payload: axios.get<IUo2oPassport>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IUo2oPassport> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_UO2OPASSPORT,
    payload: axios.get<IUo2oPassport>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IUo2oPassport> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_UO2OPASSPORT,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const updateEntity: ICrudPutAction<IUo2oPassport> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_UO2OPASSPORT,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IUo2oPassport> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_UO2OPASSPORT,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
