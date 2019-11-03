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

import { IUo2oCitizenDTOMF, defaultValue } from 'app/shared/model/uo-2-o-citizen-dtomf.model';

export const ACTION_TYPES = {
  SEARCH_UO2OCITIZENDTOMFS: 'uo2oCitizenDTOMF/SEARCH_UO2OCITIZENDTOMFS',
  FETCH_UO2OCITIZENDTOMF_LIST: 'uo2oCitizenDTOMF/FETCH_UO2OCITIZENDTOMF_LIST',
  FETCH_UO2OCITIZENDTOMF: 'uo2oCitizenDTOMF/FETCH_UO2OCITIZENDTOMF',
  CREATE_UO2OCITIZENDTOMF: 'uo2oCitizenDTOMF/CREATE_UO2OCITIZENDTOMF',
  UPDATE_UO2OCITIZENDTOMF: 'uo2oCitizenDTOMF/UPDATE_UO2OCITIZENDTOMF',
  DELETE_UO2OCITIZENDTOMF: 'uo2oCitizenDTOMF/DELETE_UO2OCITIZENDTOMF',
  RESET: 'uo2oCitizenDTOMF/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IUo2oCitizenDTOMF>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type Uo2oCitizenDTOMFState = Readonly<typeof initialState>;

// Reducer

export default (state: Uo2oCitizenDTOMFState = initialState, action): Uo2oCitizenDTOMFState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_UO2OCITIZENDTOMFS):
    case REQUEST(ACTION_TYPES.FETCH_UO2OCITIZENDTOMF_LIST):
    case REQUEST(ACTION_TYPES.FETCH_UO2OCITIZENDTOMF):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_UO2OCITIZENDTOMF):
    case REQUEST(ACTION_TYPES.UPDATE_UO2OCITIZENDTOMF):
    case REQUEST(ACTION_TYPES.DELETE_UO2OCITIZENDTOMF):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_UO2OCITIZENDTOMFS):
    case FAILURE(ACTION_TYPES.FETCH_UO2OCITIZENDTOMF_LIST):
    case FAILURE(ACTION_TYPES.FETCH_UO2OCITIZENDTOMF):
    case FAILURE(ACTION_TYPES.CREATE_UO2OCITIZENDTOMF):
    case FAILURE(ACTION_TYPES.UPDATE_UO2OCITIZENDTOMF):
    case FAILURE(ACTION_TYPES.DELETE_UO2OCITIZENDTOMF):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_UO2OCITIZENDTOMFS):
    case SUCCESS(ACTION_TYPES.FETCH_UO2OCITIZENDTOMF_LIST): {
      const links = parseHeaderForLinks(action.payload.headers.link);

      return {
        ...state,
        loading: false,
        links,
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links),
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    }
    case SUCCESS(ACTION_TYPES.FETCH_UO2OCITIZENDTOMF):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_UO2OCITIZENDTOMF):
    case SUCCESS(ACTION_TYPES.UPDATE_UO2OCITIZENDTOMF):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_UO2OCITIZENDTOMF):
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

const apiUrl = 'api/uo-2-o-citizen-dtomfs';
const apiSearchUrl = 'api/_search/uo-2-o-citizen-dtomfs';

// Actions

export const getSearchEntities: ICrudSearchAction<IUo2oCitizenDTOMF> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_UO2OCITIZENDTOMFS,
  payload: axios.get<IUo2oCitizenDTOMF>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`)
});

export const getEntities: ICrudGetAllAction<IUo2oCitizenDTOMF> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_UO2OCITIZENDTOMF_LIST,
    payload: axios.get<IUo2oCitizenDTOMF>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IUo2oCitizenDTOMF> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_UO2OCITIZENDTOMF,
    payload: axios.get<IUo2oCitizenDTOMF>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IUo2oCitizenDTOMF> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_UO2OCITIZENDTOMF,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const updateEntity: ICrudPutAction<IUo2oCitizenDTOMF> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_UO2OCITIZENDTOMF,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IUo2oCitizenDTOMF> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_UO2OCITIZENDTOMF,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
