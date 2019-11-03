import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './bo-2-m-owner-dto.reducer';
import { IBo2mOwnerDTO } from 'app/shared/model/bo-2-m-owner-dto.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBo2mOwnerDTODetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class Bo2mOwnerDTODetail extends React.Component<IBo2mOwnerDTODetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { bo2mOwnerDTOEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="sampleappApp.bo2mOwnerDTO.detail.title">Bo2mOwnerDTO</Translate> [<b>{bo2mOwnerDTOEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">
                <Translate contentKey="sampleappApp.bo2mOwnerDTO.name">Name</Translate>
              </span>
            </dt>
            <dd>{bo2mOwnerDTOEntity.name}</dd>
          </dl>
          <Button tag={Link} to="/entity/bo-2-m-owner-dto" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/bo-2-m-owner-dto/${bo2mOwnerDTOEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ bo2mOwnerDTO }: IRootState) => ({
  bo2mOwnerDTOEntity: bo2mOwnerDTO.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Bo2mOwnerDTODetail);
